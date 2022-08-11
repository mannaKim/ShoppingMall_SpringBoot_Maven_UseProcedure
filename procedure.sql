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
    p_id IN member.id%type,
    p_curvar OUT SYS_REFCURSOR
)
IS
    result_cur SYS_REFCURSOR;
BEGIN
    OPEN result_cur FOR SELECT * FROM member WHERE id=p_id;
    p_curvar := result_cur;
END;