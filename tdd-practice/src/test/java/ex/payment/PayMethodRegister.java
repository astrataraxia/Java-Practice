package ex.payment;

import java.util.List;

public class PayMethodRegister {

    private PayMethodRegReqValidator validator;
    private UserChecker userChecker;
    private CardValidityChecker cardValidityChecker;
    private PayMethodRepository payMethodRepository;

    public PayMethodRegister(PayMethodRegReqValidator validator,
                             UserChecker userChecker,
                             CardValidityChecker cardValidityChecker,
                             PayMethodRepository payMethodRepository) {
        this.validator = validator;
        this.userChecker = userChecker;
        this.cardValidityChecker = cardValidityChecker;
        this.payMethodRepository = payMethodRepository;
    }


    public Integer register(PayMethodRegReq regReq) {
        List<String> errors = validator.validate(regReq);
        if (!errors.isEmpty()) {
            throw new PayMethodRegException();
        }
        if (!userChecker.exist(regReq.getUserId())) {
            throw new NoUserException();
        }
        if (cardValidityChecker.checkValidity(regReq.getCardNo()) != CardValidity.VALID) {
            throw new InvalidCardException();
        }
        PayMethod pay = payMethodRepository.findByUserIdAndCardNo(regReq.getUserId(), regReq.getCardNo());
        if (pay != null) {
            throw new DupCardNoException();
        }
        PayMethod newPay = new PayMethod(null, regReq.getUserId(), regReq.getCardNo(), regReq.getHolderName());
        payMethodRepository.save(newPay);
        return newPay.getId();
    }
}
