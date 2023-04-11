package com.yjjjwww.tabling.manager.service;

import com.yjjjwww.tabling.common.UserType;
import com.yjjjwww.tabling.common.UserVo;
import com.yjjjwww.tabling.config.JwtTokenProvider;
import com.yjjjwww.tabling.exception.CustomException;
import com.yjjjwww.tabling.exception.ErrorCode;
import com.yjjjwww.tabling.manager.entity.Manager;
import com.yjjjwww.tabling.manager.model.ManagerRestaurantDto;
import com.yjjjwww.tabling.manager.model.ManagerSignInForm;
import com.yjjjwww.tabling.manager.model.ManagerSignUpForm;
import com.yjjjwww.tabling.manager.model.RestaurantRegisterForm;
import com.yjjjwww.tabling.manager.model.RestaurantReservationDto;
import com.yjjjwww.tabling.manager.repository.ManagerRepository;
import com.yjjjwww.tabling.reservation.entity.Reservation;
import com.yjjjwww.tabling.reservation.repository.ReservationRepository;
import com.yjjjwww.tabling.restaurant.entity.Restaurant;
import com.yjjjwww.tabling.restaurant.repository.RestaurantRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;
    private final JwtTokenProvider provider;

    private static final String SIGNUP_SUCCESS = "회원가입 성공";
    private static final String PARTNER_SUCCESS = "파트너 가입 완료";
    private static final String REGISTER_RESTAURANT_SUCCESS = "매장 등록 완료";

    @Override
    public String signUp(ManagerSignUpForm managerSignUpForm) {
        Optional<Manager> optionalManager =
            managerRepository.findByUserId(managerSignUpForm.getUserId());
        if (optionalManager.isPresent()) {
            throw (new CustomException(ErrorCode.ALREADY_SIGNUP_ID));
        }

        if (!isValidPassword(managerSignUpForm.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        if (!isValidPhone(managerSignUpForm.getPhone())) {
            throw new CustomException(ErrorCode.INVALID_PHONE);
        }

        String encPassword = BCrypt.hashpw(managerSignUpForm.getPassword(), BCrypt.gensalt());

        Manager manager = Manager.builder()
            .userId(managerSignUpForm.getUserId())
            .password(encPassword)
            .phone(managerSignUpForm.getPhone())
            .partnerYn(false)
            .build();

        managerRepository.save(manager);

        return SIGNUP_SUCCESS;
    }

    @Override
    public String signIn(ManagerSignInForm managerSignInForm) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Optional<Manager> optionalManager =
            managerRepository.findByUserId(managerSignInForm.getUserId());

        if (optionalManager.isEmpty() || !encoder.matches(managerSignInForm.getPassword(),
            optionalManager.get().getPassword())) {
            throw new CustomException(ErrorCode.LOGIN_CHECK_FAIL);
        }

        Manager manager = optionalManager.get();

        return provider.createToken(manager.getUserId(), manager.getId(), UserType.MANAGER);
    }

    @Override
    public String getPartner(String token) {

        UserVo vo = provider.getUserVo(token);

        Manager manager = managerRepository.findByUserId(vo.getUserId())
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (manager.isPartnerYn()) {
            throw new CustomException(ErrorCode.ALREADY_PARTNER);
        }

        manager.setPartnerYn(true);

        managerRepository.save(manager);

        return PARTNER_SUCCESS;
    }

    @Override
    public String registerRestaurant(String token, RestaurantRegisterForm form) {

        UserVo vo = provider.getUserVo(token);

        Manager manager = managerRepository.findByUserId(vo.getUserId())
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!manager.isPartnerYn()) {
            throw new CustomException(ErrorCode.NOT_PARTNER_MANAGER);
        }

        if (!isValidPhone(form.getPhone())) {
            throw new CustomException(ErrorCode.INVALID_PHONE);
        }

        Restaurant restaurant = Restaurant.builder()
            .name(form.getName())
            .address(form.getAddress())
            .phone(form.getPhone())
            .description(form.getDescription())
            .manager(manager)
            .build();

        restaurantRepository.save(restaurant);

        return REGISTER_RESTAURANT_SUCCESS;
    }

    @Override
    public List<RestaurantReservationDto> getReservations(String token) {
        UserVo vo = provider.getUserVo(token);

        List<Reservation> reservations =
            reservationRepository.findByManagerIdAndAcceptedFalse(vo.getId());

        List<RestaurantReservationDto> result = new ArrayList<>();

        for (Reservation reservation : reservations) {
            ManagerRestaurantDto restaurantDto = ManagerRestaurantDto.builder()
                .id(reservation.getRestaurant().getId())
                .name(reservation.getRestaurant().getName())
                .build();

            RestaurantReservationDto reservationDto = RestaurantReservationDto.builder()
                .id(reservation.getId())
                .reservationTime(reservation.getReservationTime())
                .restaurant(restaurantDto)
                .build();

            result.add(reservationDto);
        }

        return result;
    }

    /**
     * 핸드폰 번호 형식 검사
     */
    private static boolean isValidPhone(String phone) {
        if (phone.length() > 11 || phone.length() < 5) {
            return false;
        }
        boolean err = false;
        String regex = "^[0-9]*$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phone);
        if (m.matches()) {
            err = true;
        }
        return err;
    }

    /**
     * 비밀번호 형식 검사(최소 8자리에 숫자, 문자, 특수문자 각각 1개 이상 포함)
     */
    private static boolean isValidPassword(String password) {
        boolean err = false;
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        if (m.matches()) {
            err = true;
        }
        return err;
    }
}
