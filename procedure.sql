create or replace PROCEDURE getBestNewProduct(
    p_curvar1 OUT SYS_REFCURSOR,
    p_curvar2 OUT SYS_REFCURSOR,
    p_curvar3 OUT SYS_REFCURSOR
)
IS
BEGIN
    OPEN p_curvar1 FOR
        SELECT * FROM new_pro_view;
    OPEN p_curvar2 FOR
        SELECT * FROM best_pro_view;
    OPEN p_curvar3 FOR
        SELECT * FROM banner WHERE order_seq<=5 ORDER BY order_seq;
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


create or replace PROCEDURE getKindList(
    p_kind IN product.kind%TYPE,
    p_curvar OUT SYS_REFCURSOR
)
IS
BEGIN
    OPEN p_curvar FOR SELECT * FROM product WHERE kind=p_kind;
END;


create or replace PROCEDURE getProduct(
    p_pseq IN product.pseq%TYPE,
    p_curvar OUT SYS_REFCURSOR
)
IS
BEGIN
    OPEN p_curvar FOR SELECT * FROM product WHERE pseq=p_pseq;
END;


create or replace PROCEDURE insertCart(
    p_id IN cart.id%TYPE,
    p_pseq IN cart.pseq%TYPE,
    p_quantity IN cart.quantity%TYPE
)
IS
BEGIN
    INSERT INTO cart(cseq, id, pseq, quantity)
    VALUES(cart_seq.nextVal, p_id, p_pseq, p_quantity);
    COMMIT;
END;


create or replace PROCEDURE listCart(
    p_id IN cart_view.id%TYPE,
    p_curvar OUT SYS_REFCURSOR
)
IS
BEGIN
    OPEN p_curvar FOR SELECT * FROM cart_view WHERE id=p_id;
END;


create or replace PROCEDURE deleteCart(
    p_cseq IN cart.cseq%TYPE
)
IS
BEGIN
    DELETE FROM cart WHERE cseq=p_cseq;
    COMMIT;
END;


create or replace PROCEDURE insertOrder(
    p_id IN orders.id%TYPE,
    p_oseq OUT orders.oseq%TYPE
)
IS
    temp_cur SYS_REFCURSOR;
    v_oseq orders.oseq%TYPE;
    v_cseq cart.cseq%TYPE;
    v_pseq cart.pseq%TYPE;
    v_quantity cart.quantity%TYPE;
BEGIN
    -- orders ???????????? ????????? ??????
    INSERT INTO orders(oseq, id) VALUES(ORDERS_SEQ.nextval, p_id);
    -- orders ??????????????? ?????? ??? oseq ?????? 
    SELECT MAX(oseq) INTO v_oseq FROM orders;
    -- oseq ?????? OUT ????????? ??????
    p_oseq := v_oseq;
    -- cart ??????????????? id??? ?????? ??????
    OPEN temp_cur FOR 
        SELECT cseq, pseq, quantity 
        FROM cart 
        WHERE id=p_id AND result='1';
    -- ????????? oseq??? order_detail ???????????? ????????? ??????
    LOOP
        FETCH temp_cur INTO v_cseq, v_pseq, v_quantity;
        EXIT WHEN temp_cur%NOTFOUND;
        INSERT INTO order_detail(odseq, oseq, pseq, quantity)
        VALUES(ORDER_DETAIL_SEQ.nextval, v_oseq, v_pseq, v_quantity);
        -- cart ???????????? ?????? ??????
        DELETE FROM cart WHERE cseq=v_cseq;
    END LOOP;
    COMMIT;
    -- ????????????
    EXCEPTION WHEN OTHERS THEN ROLLBACK;
END;


create or replace PROCEDURE listOrder(
    p_oseq IN order_view.oseq%TYPE,
    p_curvar OUT SYS_REFCURSOR
)
IS
BEGIN
    OPEN p_curvar FOR 
        SELECT * FROM order_view 
        WHERE oseq=p_oseq
        ORDER BY odseq;
END;


create or replace PROCEDURE insertOrderOne(
    p_id IN orders.id%TYPE,
    p_pseq IN order_view.pseq%TYPE,
    p_quantity IN order_view.quantity%TYPE,
    p_oseq OUT orders.oseq%TYPE
)
IS
    v_oseq orders.oseq%TYPE;
BEGIN
    INSERT INTO orders(oseq, id) VALUES(ORDERS_SEQ.nextval, p_id);
    SELECT MAX(oseq) INTO v_oseq FROM orders;
    p_oseq := v_oseq;
    INSERT INTO order_detail(odseq, oseq, pseq, quantity)
    VALUES(ORDER_DETAIL_SEQ.nextval, v_oseq, p_pseq, p_quantity);
    COMMIT;
    EXCEPTION WHEN OTHERS THEN ROLLBACK;
END;


create or replace PROCEDURE listOrderByIdIng(
    p_id IN order_view.id%TYPE,
    p_curvar OUT SYS_REFCURSOR
)
IS
BEGIN
    OPEN p_curvar FOR 
        SELECT DISTINCT oseq FROM order_view 
        WHERE id=p_id AND result='1'
        ORDER BY oseq DESC;
END;


create or replace PROCEDURE listOrderByIdAll(
    p_id IN order_view.id%TYPE,
    p_curvar OUT SYS_REFCURSOR
)
IS
BEGIN
    OPEN p_curvar FOR 
        SELECT DISTINCT oseq
        FROM (SELECT oseq, id FROM order_view ORDER BY result, oseq DESC)
        WHERE id=p_id;
END;


create or replace PROCEDURE getAllCount(
    p_id IN qna.id%TYPE,
    p_cnt OUT NUMBER
)
IS
    v_cnt NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_cnt FROM qna WHERE id=p_id;
    p_cnt := v_cnt;
END;


create or replace PROCEDURE listQna(
    p_id IN qna.id%TYPE,
    p_startNum IN NUMBER,
    p_endNum IN NUMBER,
    p_curvar OUT SYS_REFCURSOR
)
IS
BEGIN
    OPEN p_curvar FOR
        SELECT * FROM (
            SELECT * FROM (
                SELECT ROWNUM as rn, q.* FROM
                    ((SELECT * FROM qna WHERE id=p_id ORDER BY rep, qseq DESC) q)
            ) WHERE rn>=p_startNum
        ) WHERE rn<=p_endNum;
END;


create or replace PROCEDURE insertQna(
    p_id IN qna.id%TYPE,
    p_subject IN qna.subject%TYPE,
    p_content IN qna.content%TYPE
)
IS
BEGIN
    INSERT INTO qna(qseq, id, subject, content)
    VALUES(qna_seq.nextVal, p_id, p_subject, p_content);
    COMMIT;
END;


create or replace PROCEDURE getQna(
    p_qseq IN qna.qseq%TYPE,
    p_curvar OUT SYS_REFCURSOR
)
IS
BEGIN
    OPEN p_curvar FOR
        SELECT * FROM qna WHERE qseq=p_qseq;
END;


create or replace PROCEDURE getAdmin(
    p_id IN worker.id%TYPE,
    p_curvar OUT SYS_REFCURSOR
)
IS
BEGIN
    OPEN p_curvar FOR
        SELECT * FROM worker WHERE id=p_id;
END;


create or replace PROCEDURE getProductList(
    p_startNum IN NUMBER,
    p_endNum IN NUMBER,
    p_key IN product.name%TYPE,
    p_curvar OUT SYS_REFCURSOR
)
IS
BEGIN
    OPEN p_curvar FOR
        SELECT * FROM (
            SELECT * FROM (
                SELECT rownum AS rn, p.* FROM (
                    (SELECT * FROM product 
                    WHERE name LIKE '%'||p_key||'%'
                    ORDER BY pseq DESC) p)
            ) WHERE rn>=p_startNum
        ) WHERE rn<=p_endNum;
END;


create or replace PROCEDURE adminGetAllCount(
    P_tableName IN NUMBER,
    p_key IN product.name%TYPE,
    P_cnt OUT NUMBER
)
IS
    v_cnt NUMBER;
BEGIN
    IF p_tableName = 1
    THEN 
        SELECT COUNT(*) INTO v_cnt 
        FROM product
        WHERE name LIKE '%'||p_key||'%';
    END IF;
    p_cnt := v_cnt;
END;


create or replace PROCEDURE insertProduct(
    p_name IN product.name%TYPE,
    p_kind IN product.kind%TYPE,
    p_price1 IN product.price1%TYPE,
    p_price2 IN product.price2%TYPE,
    p_price3 IN product.price3%TYPE,
    p_content IN product.content%TYPE,
    p_image IN product.image%TYPE
)
IS
BEGIN
    INSERT INTO product(pseq, name, kind, price1, price2, price3, content, image)
    VALUES(PRODUCT_SEQ.nextval, p_name, p_kind, p_price1, p_price2, p_price3, p_content, p_image);
    COMMIT;
END;


CREATE TABLE banner(
	bseq number(5) primary key,
	name varchar2(100),
	order_seq number(1),
	image varchar(50),
	useyn char(1) default 'y',
	indate date default sysdate
);

CREATE sequence banner_seq start with 1;

select * from banner;


create or replace PROCEDURE getBannerList(
    p_curvar OUT SYS_REFCURSOR
)
IS
BEGIN
    OPEN p_curvar FOR
        SELECT * FROM banner ORDER BY useyn DESC, order_seq ASC;
END;


create or replace PROCEDURE insertBanner(
    p_name IN banner.name%TYPE,
    p_order_seq IN banner.order_seq%TYPE,
    p_useyn IN banner.useyn%TYPE,
    p_image IN banner.image%TYPE
)
IS
BEGIN
    INSERT INTO banner(bseq, name, order_seq, useyn, image)
    VALUES(banner_seq.nextval, p_name, p_order_seq, p_useyn, p_image);
    COMMIT;
END;