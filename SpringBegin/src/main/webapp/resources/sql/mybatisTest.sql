---- ==== *** Spring myBatisTest *** ==== ----

create table spring_mybatistest
(no         number
,name       varchar2(20)
,email      varchar2(30)
,tel        varchar2(20)
,addr       varchar2(200)
,writeday   date default sysdate
,constraint PK_spring_mybatistest_no primary key(no)
);

create sequence seq_mybatistest
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;


select *
from spring_mybatistest
order by no desc;

/*
insert into spring_mybatistest(no, name, email, tel, addr, writeday)
values(seq_mybatistest.nextval, '홍길동'||seq_mybatistest.nextval, 'hongkd@gmail.com', '010-234-5678', '서울시 종로구 인사동', default);
*/
select*from spring_mybatistest;

-------------- 성별을 알려주는 함수 구하기

select*from hr.employees;
select*from hr.departments;

create or replace function func_gender(jubun in varchar2)
return varchar2
is v_gender varchar2(10);
begin
    v_gender:= case when substr(jubun,7,1) in ('1','3') then '남' else '여' end;
    return v_gender;
end;

create or replace function func_age(p_jubun in varchar2)
return number
is v_birthyear number(4);
begin
    if substr(p_jubun,7,1)in('1','2') 
    then v_birthyear:= to_number(substr(p_jubun,1,2))+1900;
    else v_birthyear:= to_number(substr(p_jubun,1,2))+2000;
    end if;
    return extract(year from sysdate) - v_birthyear +1;
end func_age;

select func_age('0011163358225') from dual;

create or replace function func_yearpay
(p_employee_id in HR.employees.employee_id%type)
return number
is
    v_yearpay number;
begin
    select nvl(salary+salary*commission_pct, salary)*12 into v_yearpay
    from hr.employees
    where employee_id = p_employee_id;
    
    return v_yearpay;
end func_yearpay;
select*from hr.employees;
select A.department_id, department_name, employee_id,first_name||last_name,jubun,func_gender(jubun),func_age(jubun),func_yearpay(employee_id)
from hr.departments A join hr.employees B on A.department_id = B.department_id;
select*from hr.departments;
select*from hr.employees;
select distinct nvl(department_id,-9999) as department_id from hr.departments order by department_id;
select sysdate - to_date('2013-12-05','yyyy-mm-dd') from dual;
select*from hr.employees;
select distinct nvl(department_id,-9999) as department_id from hr.departments order by department_id;


------------------------ 차트 그리기 ------------------------------
------- 남 녀 퍼센테이지 구하기

select func_gender(jubun) as gender, count(*) as gendercnt, round(count(*)/(select count(*)from hr.employees)*100,1) as percentage
from hr.employees
group by func_gender(jubun);

select decode(trunc(func_age(jubun),-1),0,'10대미만',trunc(func_age(jubun),-1)||'대') as age, count(*) as cnt
from hr.employees
group by trunc(func_age(jubun),-1)
order by trunc(func_age(jubun),-1);

select nvl(department_id,-99) as deptno, count(*) as cnt
from hr.employees
group by department_id
order by department_id;