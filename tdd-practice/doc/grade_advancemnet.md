## grade advancement

내용
* 전체 학생 회원 승급
* DB의 학년 관련 칼럼 값을 1 증가

고려사항
* 중간에 실패하면 재시도 가능해야 함
* 중간에 실패하면 원래 학년으로 복구 할 수 있어야 함.


## 기능

<img src="\tdd-practice\images\advanced-digram.png"></img>
<img src="\tdd-practice\images\advanced.png"></img>

1. GradeAdvancedService : 승급과정을 처리하는 서비스 
2. States : 실제 상태를 읽어오고 변경하는 클래스
3. AdvanceState : 상태를 나타내는 Enum
4. TargetGen : 대상자를 추출하는 역할
5. Targets : 생성한 대상자 목록을 담고있는 클래스
6. TargetsExporter : 타겟을 추출하고 저장해둘 Exporter.
7. AdvanceApplier : 실제 승급 적용 및 결과를 알려주는 클래스
8. GradeRevokeService : 만약 승급이 중간에 실패하거나 되돌려야 되는 상황일때 사용되는 서비스
9. TargetsImporter : 대상자들을 저장되어있는 목록
10. GradeRevoker : 실제 복구하는 기능

### 구현은 어디서 부터 어떻게 들어가야 될까?

 - 실제 TDD를 통해 구현할때 이렇게 구상하고 들어간다면 어디서 부터 어떻게 구현해야 할까?
 - GradeAdvanceService를 구현한다고 했을때, 주변의 영향을 주고받는 객체들을 대역을 사용해서 구현해 들어가보자.

 