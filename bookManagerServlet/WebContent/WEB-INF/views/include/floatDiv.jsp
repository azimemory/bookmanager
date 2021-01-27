<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<aside class="wrap_float-div">
	<div class="float-div"><!-- lnb -->
        <span>Menu</span>
        <ul class="fdlist"><!-- d1 -->
        	<li class="d1">
        		<a href="<%=request.getContextPath()%>/index/index.do">
	            	<i class="fas fa-search-plus"></i>
	                <span>도서검색</span>
                </a>
			</li>
			<li class="d1">
				<a href="<%=request.getContextPath()%>/cart/cart.do">
					<i class="fas fa-cart-plus"></i>
	                <span>장바구니</span>
                </a>
			</li>
            <li class="d1">
	            <a href="<%=request.getContextPath()%>/board/list.do">
	              <i class="far fa-clipboard"></i>
	              <span>게시판</span>
               	</a>
            </li>
            <li class="d1">
            	<a href="<%=request.getContextPath()%>/mypage/mypage.do">
            	<i class="fas fa-users"></i>
                <span>MyPage</span>
                </a>
            </li>
        </ul><!-- // d1 -->
    </div>
	</aside> 