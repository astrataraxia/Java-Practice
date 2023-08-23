1. parser-cal - 계산기 만들기(최범균님의 유튜브 영상) 
   1. 렉서 구현 - 토큰 변환
      1. 1 + a3, Token으로 추출.  
      TOKEN(NUMBER, "1"), TOKEN(PLUS, "+"), TOKEN(ID, "a3"), TOKEN(EOF, null)

   2. 파서 구현 - 렉서의 토큰을 트리 형태로 바꿔주는것
      1. 표헌식(Expression) - 값
         - 1 ->  숫자 식, 
         - 1 + 2 -> 이항연산자 식
         - -10 -> 단항연산자 식
         - a1 -> 식별자 식
   
      2. 구문(Statement) - 명령
          - let a1 = 10 -> let 구문
          - 1 + 1 -> 표현식을 담은 식구문(1+1은 표현식. 이것을 담고있는 식을 구문으로 사용)
      
   3. 평가기 만들기 
      1. 파서가 생성한 AST(Abstract Syntax Tree)를 순회하면서 값을 생성
         - 계산기능(좀 더 정확한 계산을 위해 BigDecimal 사용)