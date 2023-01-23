#Online Exam management system use cases

##UC1: Sign up 
    primary actor: visitor
    preccondition: having client application
    Main success Senario: 
        1. user registers by giving required info
        2. the system registers the data to DB

    Exeptions: 
        2.a) a user has already registered with same phone number:
                reject registeration
        1.a) user has not given necesery info:
               reject registration 
##UC2: Sign in
        p.Actor: user
        precondition: registered user
        MSS:
            1. user provides credentials
            2. system chcks credentials and log in
    
        Exceptions:
            2.a) credential dont match:
                reject
            1.a) empty credential:
                reject
##UC3: Log out
        P.A: user
        pre.cond:logged in
        MSS:
            1. user logs him self out

##UC4: create  organization
        P.A: user
        pre.cond:logged in
        MSS:


##UC5: Assign org.Managers
    P.A:org.creator/manager
    pre.cond:organaization and managr exist
    MSS:
        1. Previous manager assigns new manager
        2. New manager accepts the position
        3. System assigns new manager as current manager
    Exception:
        1.a) New manager has not registered to system;
            Inform to register and reject.
        1.b) Previous manager don't have the previlege;
            Inform and reject.
        2.a) New manager denies the position;
            system rejects the assignment.


##UC6: Create an exam:
    P.A. Organisation manager
    Pre condition:
    MSS:
        1. Organisation manager create an exam by providing required info. 
        2. Organisation manager create question for the exam.

    Exceptions:
        1. 


##UC7: Apply for an exam
    P.A. Exam Taker
    Pre condition: Logged in
    MSS: 
        1. User apply to take an exam.
        2. Manager approve applications.
        3. System registers the user for the exam.

    Exceptions:
        1.a) Deadline reached; Reject.
        2.a) Manager disprove; Reject.



##UC8: Take exam
    P.A. Exam Taker
    Pre condition: 
        1. User registered for the exam.
        2. Exam started and not ended.
    MSS:
        1. System shows randomised questions of the exam.
        2. Exam takes attempts to answer all questions.
        3. Exam taker submits his answers when done.

    Exceptions:
        1.a) Exam did not start; show count down.
        2.a) Given duration used up; auto submit.

 
