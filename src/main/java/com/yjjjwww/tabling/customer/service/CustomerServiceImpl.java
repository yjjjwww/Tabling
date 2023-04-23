package com.yjjjwww.tabling.customer.service;

import com.yjjjwww.tabling.common.UserType;
import com.yjjjwww.tabling.common.UserVo;
import com.yjjjwww.tabling.config.JwtTokenProvider;
import com.yjjjwww.tabling.customer.entity.Customer;
import com.yjjjwww.tabling.customer.model.CustomerSignInForm;
import com.yjjjwww.tabling.customer.model.CustomerSignUpForm;
import com.yjjjwww.tabling.customer.model.ManagerDto;
import com.yjjjwww.tabling.customer.model.RegisterReviewForm;
import com.yjjjwww.tabling.customer.model.ReserveRestaurantForm;
import com.yjjjwww.tabling.customer.model.RestaurantDto;
import com.yjjjwww.tabling.customer.model.VisitRestaurantForm;
import com.yjjjwww.tabling.customer.repository.CustomerRepository;
import com.yjjjwww.tabling.exception.CustomException;
import com.yjjjwww.tabling.exception.ErrorCode;
import com.yjjjwww.tabling.manager.entity.Manager;
import com.yjjjwww.tabling.manager.repository.ManagerRepository;
import com.yjjjwww.tabling.reservation.entity.Reservation;
import com.yjjjwww.tabling.reservation.repository.ReservationRepository;
import com.yjjjwww.tabling.restaurant.entity.Restaurant;
import com.yjjjwww.tabling.restaurant.repository.RestaurantRepository;
import com.yjjjwww.tabling.review.entity.Review;
import com.yjjjwww.tabling.review.repository.ReviewRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;
    private final ManagerRepository managerRepository;
    private final ReviewRepository reviewRepository;
    private final JwtTokenProvider provider;

    private static final String SIGNUP_SUCCESS = "회원가입 성공";
    private static final String VISIT_SUCCESS = "매장 방문 확인";
    private static final String REGISTER_REVIEW_SUCCESS = "리뷰 작성 성공";

    @Override
    public String signUp(CustomerSignUpForm customerSignUpForm) {
        Optional<Customer> optionalCustomer =
            customerRepository.findByUserId(customerSignUpForm.getUserId());
        if (optionalCustomer.isPresent()) {
            throw (new CustomException(ErrorCode.ALREADY_SIGNUP_ID));
        }

        if (!isValidPassword(customerSignUpForm.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        if (!isValidPhone(customerSignUpForm.getPhone())) {
            throw new CustomException(ErrorCode.INVALID_PHONE);
        }

        String encPassword = BCrypt.hashpw(customerSignUpForm.getPassword(), BCrypt.gensalt());

        Customer customer = Customer.builder()
            .userId(customerSignUpForm.getUserId())
            .password(encPassword)
            .phone(customerSignUpForm.getPhone())
            .build();

        customerRepository.save(customer);

        return SIGNUP_SUCCESS;
    }

    @Override
    public String signIn(CustomerSignInForm customerSignInForm) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Optional<Customer> optionalCustomer =
            customerRepository.findByUserId(customerSignInForm.getUserId());

        if (optionalCustomer.isEmpty() || !encoder.matches(customerSignInForm.getPassword(),
            optionalCustomer.get().getPassword())) {
            throw new CustomException(ErrorCode.LOGIN_CHECK_FAIL);
        }

        Customer customer = optionalCustomer.get();

        return provider.createToken(customer.getUserId(), customer.getId(), UserType.CUSTOMER);
    }

    @Override
    public List<RestaurantDto> getRestaurantList() {

        List<Restaurant> restaurants = restaurantRepository.findAll(
            Sort.by(Sort.Direction.DESC, "createdAt"));

        ArrayList<RestaurantDto> result = new ArrayList<>();

        for (Restaurant restaurant : restaurants) {
            ManagerDto managerDto = ManagerDto.builder()
                .id(restaurant.getManager().getId())
                .userId(restaurant.getManager().getUserId())
                .phone(restaurant.getManager().getPhone())
                .build();

            RestaurantDto restaurantDto = RestaurantDto.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .phone(restaurant.getPhone())
                .description(restaurant.getDescription())
                .manager(managerDto)
                .build();

            result.add(restaurantDto);
        }

        return result;
    }

    @Override
    public String reserveRestaurant(String token, ReserveRestaurantForm form) {
        UserVo vo = provider.getUserVo(token);

        Customer customer = customerRepository.findByUserId(vo.getUserId())
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Restaurant restaurant = restaurantRepository.findById(form.getRestaurantId())
            .orElseThrow(() -> new CustomException(ErrorCode.RESTAURANT_NOT_FOUND));

        Manager manager = managerRepository.findById(restaurant.getManager().getId())
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        String code = UUID.randomUUID().toString().replace("-", "");

        Reservation reservation = Reservation.builder()
            .reservationTime(form.getReservationTime())
            .reservationCode(code)
            .visited(false)
            .accepted(false)
            .restaurant(restaurant)
            .manager(manager)
            .customer(customer)
            .build();

        reservationRepository.save(reservation);

        return code;
    }

    @Override
    public String visitRestaurant(String token, VisitRestaurantForm form) {
        String reservationCode = form.getReservationCode();
        Long restaurantId = form.getRestaurantId();

        Reservation reservation =
            reservationRepository.findByReservationCode(reservationCode)
                .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));

        UserVo vo = provider.getUserVo(token);

        if (!Objects.equals(reservation.getCustomer().getId(), vo.getId())) {
            throw new CustomException(ErrorCode.CUSTOMER_NOT_MATCH);
        }

        if (!Objects.equals(restaurantId, reservation.getRestaurant().getId())) {
            throw new CustomException(ErrorCode.RESTAURANT_NOT_MATCH);
        }

        if (!reservation.isAccepted()) {
            throw new CustomException(ErrorCode.NOT_ACCEPTED);
        }

        LocalDateTime curTime = LocalDateTime.now();
        if (!curTime.isBefore(reservation.getReservationTime().minusMinutes(10))) {
            throw new CustomException(ErrorCode.RESERVATION_TIME_LATE);
        }

        if (reservation.isVisited()) {
            throw new CustomException(ErrorCode.ALREADY_VISITED);
        }

        reservation.setVisited(true);
        reservationRepository.save(reservation);

        return VISIT_SUCCESS;
    }

    @Override
    public String registerReview(String token, RegisterReviewForm form) {
        UserVo vo = provider.getUserVo(token);

        Long reservationId = form.getReservationId();
        Reservation reservation =
            reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));

        if (!Objects.equals(reservation.getCustomer().getId(), vo.getId())) {
            throw new CustomException(ErrorCode.CUSTOMER_NOT_MATCH);
        }

        if (!reservation.isVisited()) {
            throw new CustomException(ErrorCode.NOT_VISITED_RESERVATION);
        }

        Optional<Review> optionalReview = reviewRepository.findByReservationId(reservationId);
        if (optionalReview.isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_REGISTERED_REVIEW);
        }

        if (form.getRating() < 0 || form.getRating() > 10) {
            throw new CustomException(ErrorCode.INVALID_RATING);
        }

        if ("".equals(form.getContents())) {
            throw new CustomException(ErrorCode.INVALID_CONTENTS);
        }

        Review review = Review.builder()
            .contents(form.getContents())
            .rating(form.getRating())
            .reservation(reservation)
            .restaurant(reservation.getRestaurant())
            .customer(reservation.getCustomer())
            .build();

        reviewRepository.save(review);

        return REGISTER_REVIEW_SUCCESS;
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
