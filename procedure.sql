create or replace PROCEDURE getBestNewProduct(
    p_curvar1 OUT SYS_REFCURSOR,
    p_curvar2 OUT SYS_REFCURSOR
)
IS
BEGIN
    OPEN p_curvar1 FOR
        SELECT * FROM new_pro_view;
    OPEN p_curvar2 FOR
        SELECT * FROM best_pro_view;
END;


create or replace PROCEDURE getMember(
    p_id IN member.id%TYPE,
    p_curvar OUT SYS_REFCURSOR
)
IS
    result_cur SYS_REFCURSOR;
BEGIN
    OPEN result_cur FOR SELECT * FROM member WHERE id=p_id;
    p_curvar := result_cur;
END;


create or replace PROCEDURE insertMember(
    p_id IN member.id%TYPE,
    p_pwd IN member.pwd%TYPE,
    p_name IN member.name%TYPE,
    p_email IN member.email%TYPE,
    p_phone IN member.phone%TYPE,
    p_zip_num IN member.zip_num%TYPE,
    p_address1 IN member.address1%TYPE,
    p_address2 IN member.address2%TYPE,
    p_address3 IN member.address3%TYPE
)
IS
BEGIN
    INSERT INTO member(id, pwd, name, email, phone, zip_num, address1, address2, address3)
    VALUES(p_id, p_pwd, p_name, p_email, p_phone, p_zip_num, p_address1, p_address2, p_address3);
    COMMIT;
END;


create or replace PROCEDURE updateMember(
    p_id IN member.id%TYPE,
    p_pwd IN member.pwd%TYPE,
    p_name IN member.name%TYPE,
    p_email IN member.email%TYPE,
    p_phone IN member.phone%TYPE,
    p_zip_num IN member.zip_num%TYPE,
    p_address1 IN member.address1%TYPE,
    p_address2 IN member.address2%TYPE,
    p_address3 IN member.address3%TYPE
)
IS
BEGIN
    UPDATE member 
    SET pwd=p_pwd, name=p_name, email=p_email, phone=p_phone,
        zip_num=p_zip_num, address1=p_address1, address2=p_address2, address3=p_address3
    WHERE id=p_id;
    COMMIT;
END;