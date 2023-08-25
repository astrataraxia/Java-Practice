package ex.payment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class PayMethodRegisterTest {

    PayMethodRegReqValidator mockValidator = mock(PayMethodRegReqValidator.class);
    UserChecker mockUserChecker = mock(UserChecker.class);
    CardValidityChecker mockCardChecker = mock(CardValidityChecker.class);

    private PayMethodRegister pr;
    private PayMethodRepository payMethodRepository = new MemoryPayMethodRepository();

    @BeforeEach
    void setUp() {
        pr = new PayMethodRegister(mockValidator, mockUserChecker, mockCardChecker, payMethodRepository);
    }

    @Test
    void invalidRegReq() {
        given(mockValidator.validate(any(PayMethodRegReq.class))).willReturn(Arrays.asList("some.error"));

        PayMethodRegReq regReq = new PayMethodRegReq(null, null, null);

        assertThatThrownBy(() -> pr.register(regReq))
                .isInstanceOf(PayMethodRegException.class);
    }

    @Test
    void noUser() {
        given(mockValidator.validate(any(PayMethodRegReq.class))).willReturn(Arrays.asList());
        given(mockUserChecker.exist("111")).willReturn(false);

        PayMethodRegReq regReq = new PayMethodRegReq("111", null, null);
        assertThatThrownBy(() -> pr.register(regReq))
                .isInstanceOf(NoUserException.class);
    }

    @Test
    void invalidCardNo() {
        given(mockValidator.validate(any(PayMethodRegReq.class))).willReturn(Arrays.asList());
        given(mockUserChecker.exist("111")).willReturn(true);
        given(mockCardChecker.checkValidity("1234")).willReturn(CardValidity.EXPIRED);

        PayMethodRegReq regReq = new PayMethodRegReq("111", "1234", null);
        assertThatThrownBy(() -> pr.register(regReq))
                .isInstanceOf(InvalidCardException.class);
    }

    @Test
    void dupCardNo() {
        given(mockValidator.validate(any(PayMethodRegReq.class))).willReturn(Arrays.asList());
        given(mockUserChecker.exist("111")).willReturn(true);
        given(mockCardChecker.checkValidity("1234")).willReturn(CardValidity.VALID);

        payMethodRepository.save(new PayMethod(1, "111", "1234", "소유자"));
        PayMethodRegReq regReq = new PayMethodRegReq("111", "1234", "holder");

        assertThatThrownBy(() -> pr.register(regReq))
                .isInstanceOf(DupCardNoException.class);
    }

    @Test
    void registered() {
        given(mockValidator.validate(any(PayMethodRegReq.class))).willReturn(Arrays.asList());
        given(mockUserChecker.exist("111")).willReturn(true);
        given(mockCardChecker.checkValidity("1234")).willReturn(CardValidity.VALID);

        PayMethodRegReq regReq = new PayMethodRegReq("111", "1234", "holder");

        Integer id = pr.register(regReq);
        assertThat(id).isNotNull();
        PayMethod pay = payMethodRepository.findById(id);
        assertThat(pay.getCardNo()).isEqualTo("1234");
        assertThat(pay.getUserId()).isEqualTo("111");
        assertThat(pay.getHolder()).isEqualTo("holder");
    }
}
