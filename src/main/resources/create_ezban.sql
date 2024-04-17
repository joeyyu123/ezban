CREATE
    DATABASE IF NOT EXISTS ezban;
USE
    ezban;

#報名表相關以及聊天室還沒建立
drop table if exists authority;
drop table if exists birthday_coupon_holder;
drop table if exists event_comment_report;
drop table if exists event_comment;
drop table if exists func;
drop table if exists notification;
drop table if exists points_record;
drop table if exists product_comment_report;
drop table if exists product_comment;
drop table if exists product_img;
drop table if exists product_order_detail;
drop table if exists product_order;
drop table if exists birthday_coupon;
drop table if exists product_report;
drop table if exists admin;
drop table if exists qa;
drop table if exists qrcode_ticket;
drop table if exists save_event;
drop table if exists save_product;
drop table if exists product;
drop table if exists product_category;
drop table if exists ticket_order_detail;
drop table if exists ticket_order;
drop table if exists event_coupon;
drop table if exists member;
drop table if exists ticket_type;
drop table if exists event;
drop table if exists event_category;
drop table if exists host;
#
DROP TABLE IF EXISTS ticket_registration;

create table admin
(
    admin_no      int auto_increment
        primary key,
    admin_account varchar(20) null,
    admin_pwd     varchar(20) null,
    admin_mail    varchar(50) null,
    admin_phone   varchar(15) null,
    admin_status  tinyint     null,
    constraint admin_uk_1
        unique (admin_account),
    constraint admin_uk_2
        unique (admin_mail),
    constraint admin_uk_3
        unique (admin_phone)
);

INSERT INTO admin (admin_account, admin_pwd, admin_mail, admin_phone, admin_status)
VALUES ('admin1', 'password1', 'admin1@mail.com', '0911111111', 1),
       ('admin2', 'password2', 'admin2@mail.com', '0911111112', 1),
       ('admin3', 'password3', 'admin3@mail.com', '0911111113', 1),
       ('admin4', 'password4', 'admin4@mail.com', '0911111114', 1),
       ('admin5', 'password5', 'admin5@mail.com', '0911111115', 1);

create table event_category
(
    event_category_no   int auto_increment
        primary key,
    event_category_name varchar(20) not null
);

create table birthday_coupon
(
    birthday_coupon_no       int auto_increment
        primary key,
    birthday_coupon_discount int     not null,
    birthday_coupon_status   tinyint not null,
    valid_days               int     not null
);
insert into birthday_coupon(birthday_coupon_discount, birthday_coupon_status, valid_days)
values (30, 1, 30);

INSERT INTO event_category (event_category_name)
VALUES ('路跑'),
       ('音樂會'),
       ('研討會'),
       ('展覽'),
       ('運動賽事'),
       ('戲劇'),
       ('電影'),
       ('烹飪課程'),
       ('攝影課程'),
       ('繪畫課程'),
       ('舞蹈課程');

create table func
(
    func_no   int auto_increment
        primary key,
    func_name varchar(20) null
);

INSERT INTO func (func_name)
VALUES ('Function1'),
       ('Function2'),
       ('Function3');

create table authority
(
    authority_no int not null auto_increment primary key,
    admin_no     int not null,
    func_no      int not null,
    foreign key (admin_no) references admin (admin_no),
    constraint authority_func_no_func_no_fk
        foreign key (func_no) references func (func_no)
);

INSERT INTO authority (admin_no, func_no)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 1),
       (2, 2),
       (3, 1),
       (3, 2);



create table host
(
    host_no      int auto_increment
        primary key,
    host_account varchar(20) null,
    host_pwd     varchar(20) null,
    host_name    varchar(50) null,
    host_mail    varchar(50) null,
    host_phone   varchar(15) null,
    host_status  tinyint     null,
    constraint host_uk_1
        unique (host_account),
    constraint host_uk_2
        unique (host_mail),
    constraint host_uk_3
        unique (host_phone)
);

INSERT INTO host (host_account, host_pwd, host_name, host_mail, host_phone, host_status)
VALUES ('host1', 'password1', 'Host1', 'host1@gmail.com', '0911111111', 1),
       ('host2', 'password2', 'Host2', 'host2@gmail.com', '0922222222', 1),
       ('host3', 'password3', 'Host3', 'host3@gmail.com', '0933333333', 1),
       ('host4', 'password4', 'Host4', 'host4@gmail.com', '0944444444', 1),
       ('host5', 'password5', 'Host5', 'host5@gmail.com', '0955555555', 1),
       ('host6', 'password6', 'Host6', 'host6@gmail.com', '0966666666', 1),
       ('host7', 'password7', 'Host7', 'host7@gmail.com', '0977777777', 1),
       ('host8', 'password8', 'Host8', 'host8@gmail.com', '0988888888', 1),
       ('host9', 'password9', 'Host9', 'host9@gmail.com', '0999999999', 1),
       ('host10', 'password10', 'Host10', 'host10@gmail.com', '0900000010', 1);



create table event
(
    event_no                int auto_increment
        primary key,
    event_img               longblob     null,
    event_name              varchar(50)  not null,
    event_category_no       int          null,
    host_no                 int          not null,
    event_desc              longtext     null,
    event_city              varchar(15)  null,
    event_detailed_address  varchar(250) null,
    event_add_time          datetime     not null,
    event_remove_time       datetime     not null,
    registration_start_time datetime     null,
    registration_end_time   datetime     null,
    event_start_time        datetime     not null,
    event_end_time          datetime     not null,
    registered_count        int          null default 0,
    event_status            tinyint      not null,
    total_rating            int          null,
    event_rating_count      int          null,
    event_checkout_status   tinyint      null,
    event_checkout_time     int          null,
    constraint event_event_category_event_category_no_fk
        foreign key (event_category_no) references event_category (event_category_no),
    constraint event_host_host_no_fk
        foreign key (host_no) references host (host_no)
);

INSERT INTO event (event_name, event_category_no, host_no, event_desc, event_city, event_detailed_address,
                   event_add_time, event_remove_time, registration_start_time, registration_end_time, event_start_time,
                   event_end_time, event_status, total_rating, event_rating_count, event_checkout_status)
VALUES ('Skyline Film 屋頂電影院', 1, 1,
        '<p><strong>台北好久不見!</strong></p><p><strong>這次4月在內湖的屋頂放映，連續兩週，十部電影，不變的經典無線耳機與木質躺椅，遠眺著小半個台北盆地，春日徐徐微風下，人手一杯啤酒與美式燻肉，暢快舒適的屋頂體驗，限定回歸!</strong></p><p><strong>4/19(Fri)-4/21(Sun)及4/26-4/28(Sun) 這兩週，我們刻意把週五的場次縮減為一場，將整體時間延後至8:30開演，下班下課的你們可以不用那麼趕了!</strong></p><p><strong>快把時間留下來，來屋頂找我們喝一杯看電影!</strong></p><p>&nbsp;</p><p><span style="color:#c0392b"><strong>凡購票入場觀眾即可免費獲得百威金尊330ml一罐!</strong></span></p><p><strong>期待天氣很好，我們4月內湖台北建材中心屋頂見!</strong></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403260546501777605381.jpg" style="height:400px; width:600px"></p><p style="text-align:center"><strong><img alt="" src="https://static.accupass.com/eventintro/2305190800441741697587.jpg" style="height:400px; width:600px"></strong></p><hr><p><strong>4/19(Fri)</strong><br><strong>20:30 Forrest Gump 阿甘正傳&nbsp;(19:30 開放入場 Open at 19:30)</strong><br><br><span style="color:#999999">阿甘坐在公園的長凳上，向往來的人說著自己的故事。</span></p><p><span style="color:#999999">他是美式足球明星、越戰英雄、乒乓球國家隊，現在更是個成功的生意人。</span></p><p><span style="color:#999999">但如果沒有母親和Jenny，他這輩子惦記最深的兩個人，阿甘或許不會有那麽不平凡的人生。</span></p><p><span style="color:#999999">而他的故事，要從那個腦袋不太靈光，肢體天生有些缺陷的孩子說起.....</span></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403250844331285794200.jpg" style="height:402px; width:600px"></p><p>&nbsp;</p><hr><p><strong>4/20(Sat) </strong></p><p><strong>18:30 In Bruges&nbsp;</strong><strong>殺手沒有假期</strong><strong> (17:30</strong><strong>開放入場</strong><strong> Open at 17:30)</strong></p><p><span style="color:#999999">Ken和Ray在完成了刺殺任務後，奉命來到比利時的布魯日度假。</span></p><p><span style="color:#999999">在充滿觀光客的中世紀小鎮裡，他倆參訪古蹟、看看藝術展覽與畫作，雖然挺輕鬆的，但怎麼樣都不像是兩個殺手喜歡待的地方。</span></p><p><span style="color:#999999">原來Ray在上次的刺殺任務中，雖然成功消滅目標，卻也誤殺了一位無辜的小男孩，而Ken也突然接到了上級的新任務，就是要殺了Ray......</span></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403250852257420067220.jpg" style="height:420px; width:600px"></p><p><strong>21:20 The Boat That Rocked&nbsp;海盜電台</strong><strong>&nbsp;(20:40</strong><strong>開放入場</strong><strong> Open at 20:40)</strong></p><p><span style="color:#999999">在英國60年代，人們唯一能夠聽到搖滾樂的地方，是一艘大西洋飄盪的破船 ── 一個在海上發送違法電波、百無禁忌的海盜電台。<br><br>但到了上了船之後，才知道這群改變世界的不法之徒，其實只是一群被貼上各種標籤的怪胎，用著生命愛著搖滾樂。<br><br>別忘了，搖滾樂只有一個規則，就是沒有規則。</span></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403250856291296833566.jpg" style="height:399px; width:600px"></p><hr><p><strong>4/21(Sun) </strong></p><p><strong>18:30 De Surpirse&nbsp;意外製造公司</strong><strong>&nbsp;(17:30 </strong><strong>開放入場</strong><strong> Open at 17:30)</strong></p><p><span style="color:#999999">歐洲西部的一座貴族莊園，雅各冷漠地看著母親死去，他心中早已對生命失去熱情，捨棄了一切，只想要找一個平靜而無痛的死法。</span></p><p><span style="color:#999999">一次偶然的機會，他發現了這間提供人生單程票的公司，在選購了不知道死法，前往極樂世界的「驚喜包」的旅程後，他卻遇到了唯一讓他動心的旅伴…</span></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403250902237384089740.jpg" style="height:337px; width:600px"></p><p><strong>21:15&nbsp;The Lunchbox&nbsp;美味情書</strong><strong>&nbsp;(20:35&nbsp;</strong><strong>開放入場</strong><strong> Open at 20:35)</strong></p><p><span style="color:#999999">百年來，孟買的火車便當快遞乎從不出錯。</span></p><p><span style="color:#999999">IIa今天特別開心，因為他親手為丈夫做的午餐便當吃得精光。她期望著透過親手製作的美味便當，挽回兩人日漸消散的感情。</span></p><p><span style="color:#999999">但與丈夫詢問後，IIa發現他的便當居然被送到了陌生人嘴裡。她接連送了幾次，發現便當從來沒有送到丈夫那兒，但每天卻都被吃的乾乾淨淨...</span></p><p><span style="color:#999999">IIa雖然對於送錯便當感到失望，但卻想對這個每天吃光她便當的陌生人說聲感謝，於是她留下了紙條在便當內。</span></p><p><span style="color:#999999">傍晚，IIa收到了來自Saajan的第一封回信......</span></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403260505041518195971.jpg" style="height:345px; width:600px"></p><hr><p><strong>4/26(Fri) </strong></p><p><strong>20:30 Nuovo Cinema Paradiso 新天堂樂園&nbsp;(19:30 </strong><strong>開放入場</strong><strong> Open at 19:30)</strong></p><p><span style="color:#999999">多年以後，當頑童Toto成為大導演Salvatore之後，他才回到一切開始的地方—早已荒廢的「天堂樂園電影院」。<br><br>曾經，「天堂樂園電影院」是世界上最棒的地方。因為那裡有電影、有笑聲，還有一個在放映室，守候著鎮民笑容的年老放映師Adelfio。Toto的電影夢在這裡萌芽，但也為了夢想，他必須離開家鄉...<br><br>記得每一次看電影的感動，而這一次，將會更深刻...</span></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403260511115616575040.jpg" style="height:312px; width:600px"></p><hr><p><strong>4/27(Sat)</strong></p><p><strong>18:30 Love Letter&nbsp;</strong><strong>情書</strong><strong> (17:30</strong><strong>開放入場</strong><strong> Open at 17:30) </strong></p><p><span style="color:#999999">渡邊博子的未婚夫藤井樹因山難過世。她的哀傷、憤怒、思念與愛，都像一封無法投遞的信一樣，壓得她無法呼吸。<br><br>半開玩笑地，她找出了藤井樹高中的畢業紀念冊，「你好嗎？我很好……」博子照著上頭的地址寫了信給他。然後，她收到了藤井樹的回信。早已拆遷的老家、早已死去的人，早在多年前結束的故事，因此重新轉動了起來……</span></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403260514019388401890.jpg" style="height:251px; width:600px"></p><p><strong>21:30 &nbsp;Seven Psychopaths</strong><strong> 瘋狗綁票令&nbsp;(20:50</strong><strong>開放入場</strong><strong> Open at 20:50) </strong></p><p><span style="color:#999999">Marty是個編劇，他想好了新劇本的標題 - Seven Psychopaths，正苦惱著內容該如何動筆。</span></p><p><span style="color:#999999">這天，無業的演員好友Billy來訪，談論到他與另一位老人Hans正在做的工作 - 職業狗狗綁匪，而好巧不巧，他們新的受害對象，正是黑道大哥Charlie最疼愛的小西施...</span></p><p><span style="color:#999999">而Marty也成為這場無厘頭綁票的受害者，四人一狗與他們周遭一切的相愛相殺，就此展開。</span></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403260517228821475190.jpg" style="height:400px; width:600px"></p><hr><p><strong>4/28(Sun)</strong></p><p><strong>18:30 The Worst Person in the World</strong><strong> 世界上最爛的人&nbsp;(17:30</strong><strong>開放入場</strong><strong> Open at 17:30) </strong></p><p><span style="color:#999999">年輕的你，應該適合矛盾與徬徨吧?&nbsp;</span></p><p><span style="color:#999999">Julie是個聰明、自信且充滿行動力的人，在學業、職涯與感情間，她總是投其所好，但也會果斷的踩下煞車。</span></p><p><span style="color:#999999">在看似打破傳統框架下的自在人生，Julie仍舊活得像是迷途鳥兒一般，拍打著翅膀，卻不知道目的地......</span></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403260521061977443713.jpg" style="height:401px; width:600px"></p><p>&nbsp;</p><p><strong>21:30 Annie Hall</strong><strong>&nbsp;安妮霍爾&nbsp;(20:50</strong><strong>開放入場</strong><strong> Open at 20:50) </strong></p><p><span style="color:#999999">Alvy Singer fall in love with Annie Hall。</span></p><p><span style="color:#999999">這就是全部的故事，就是全部的甜蜜、浪漫、悲傷與悔恨。</span></p><p><span style="color:#999999">簡單而複雜，因為Alvy是一個矛盾的人，他是一個憂傷不得志的喜劇演員，他需要愛卻又尖酸刻薄，他神經質又自以為是。是一個充滿缺點但無法讓人討厭的人，因為懦弱的他，如此勇敢的愛，畏縮的他，如此真誠的坦率。</span></p><p><span style="color:#999999">男人的脆弱，還有女人的美，以幽默掩飾安全感...Woody Allen是沒在客氣的!</span></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403260547311383970679.jpg" style="height:338px; width:600px"></p><hr><p><strong>Skyline 屋頂限定</strong></p><p>Skyline Film 與每一位合作夥伴在屋頂，帶給你們最特別的體驗。</p><p><strong><span style="color:#c0392b">由爅登煙醺在屋頂提供的各類不同菜單(燻肉產品無包含在票價中，需額外購買)</span></strong>，以原木慢火柴燒，軟嫩夠味的手撕豬與牛前胸，香嫩帶骨的豬肋排，配上夠味的墨西哥辣椒、酸黃瓜與生洋蔥末，最後淋上秘制香料BBQ醬，來屋頂就是可以這麼享受!</p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2302200818024123072500.jpg" style="height:400px; width:600px"></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2302200818501253558762.jpg" style="height:400px; width:600px"></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2305190701221976350000.jpg" style="height:446px; width:600px"></p><p>&nbsp;</p><p><strong>1.柴燒煙燻牛前胸肉+煙燻香腸或培根.搭配墨西哥辣椒.酸黃瓜.生洋蔥.特製B.B.Q.醬及餐包</strong> <span style="color:#c0392b"><strong>$350</strong></span></p><p><strong>2.柴燒煙燻手撕豬肉+煙燻香腸或培根搭配墨西哥辣椒. 酸黃瓜.生洋蔥.特製B.B.Q.醬及餐包<span style="color:#c0392b"> $280</span></strong></p><p><strong>3.柴燒煙燻雞腿+煙燻香腸或培根搭配墨西哥辣椒. 酸黃瓜.生洋蔥.特製B.B.Q.醬及餐包<span style="color:#c0392b"> $280</span></strong></p><p><strong>4.雙人分享餐[手撕豬]+[牛前胸]or[雞腿](2選1)&nbsp;+煙燻香腸或培根搭配墨西哥辣椒. 酸黃瓜.生洋蔥.特製B.B.Q.醬及餐包</strong> <span style="color:#c0392b"><strong>$600</strong></span></p><p><strong>5.柴燒煙燻分享派對燻肉(牛前胸.雞腿.手撕豬.適合3-4人)+煙燻香腸或培根搭配墨西哥辣椒. 酸黃瓜.生洋蔥.特製B.B.Q.醬及餐包</strong> <span style="color:#c0392b"><strong>$900</strong></span></p><p><strong>6. 煙燻手撕豬肉捲餅 <span style="color:#c0392b">$150 (僅提供第二場次)</span></strong></p><p><strong>7.煙燻雞腿捲餅 <span style="color:#c0392b">$150 (僅提供第二場次)</span></strong></p><p><strong>8. 煙燻牛前胸捲餅 <span style="color:#c0392b">$200 (僅提供第二場次)</span></strong></p><p><strong><span style="color:#c0392b">無論是一個人、兩個人，還是一群人，屋頂上的肉肉party，我們通通一網打盡!&nbsp;</span></strong></p><hr><p>購票即免費贈送最具王者風範的<strong>百威金尊一罐，單一品種麥芽啤酒的純正血統，純粹豐郁口感不只是絕妙的搭餐酒，百威金尊就像是皇冠上的珍珠，是屋頂電影院絕對不能少的心頭好。來屋頂用百威金尊，搭配精緻燻肉，悠閒的下班與週末時光，就該這樣過!</strong></p><p><strong>此外，百威金尊攤位每場次都將提供限量拍照打卡禮，快來小巧精美的金尊Stand bar找我們玩!</strong></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2302130555461514491948.jpg" style="height:338px; width:600px"></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2305190703379459226790.jpg" style="height:400px; width:600px"></p><hr><p><strong><span style="background-color:#f39c12">yoxi x&nbsp;Skyline Film&nbsp;屋頂電影院&nbsp;乘車專案</span></strong></p><p>內湖不方便? 電影結束怕沒有捷運? 想喝酒又怕回不了家?<br>yoxi攜手屋頂電影院推出多樣乘車專屬優惠，讓我們來解決你的交通煩惱!</p><p><strong><span style="color:#c0392b">1.&nbsp;&nbsp;新朋友限定：</span></strong> <span style="color:#c0392b">新用戶首次搭乘前輸入 [ skf2024 ］即可兌換$200優惠 （折抵面額為$50*4）</span>。新朋友優惠領取截止日為4/29 ，領取後60天內可搭乘使用。</p><p><strong><span style="color:#c0392b">2. 新朋友老朋友一起用：</span></strong> <span style="color:#c0392b">不分新舊用戶，只要於4/5-4/29活動宣傳期間，輸入[ 屋頂電影夜］預約叫車享2趟9折優惠，單趟最高折抵 $100</span>，領取後30天內可搭乘使用!</p><p><strong><span style="color:#c0392b">3. 放映期間限定:</span></strong> 不分新舊用戶，我們的工作人員將於每場次電影開演前公布現時神秘優惠碼，現場的觀眾們要好好把握機會啦!</p><p><strong>p.s.&nbsp;yoxi的優惠券每次限使用一張，無法合併使用，用戶可自行挑選最適合當前行程的優惠券折抵。</strong></p><p><strong><span style="color:#c0392b">限時領取 yoxi x Skyline Film 的專案優惠快上:</span></strong> <a href="https://yoxirider.page.link/V8rP">https://yoxirider.page.link/V8rP</a></p><hr><p><strong>活動場地</strong></p><p>稍稍遠離了信義區，我們來到內湖IKEA對面的台北建材中心。</p><p>走上8樓的露台空間，映入眼簾的是小半個台北盆地的景緻，還能遠眺101，印象中繁忙又擁擠的內湖區，在週五及週末的傍晚時光，反倒是多了幾分悠哉感。</p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403290745281483280010.jpg" style="height:450px; width:600px"></p><p style="text-align:center"><img src="https://static.accupass.com/eventintro/2403300952038797731210.jpg" style="height:800px; width:600px"></p><p><strong>場地: 台北設計建材中心</strong></p><p><strong>地點: 台北市內湖區新湖一路185號(內湖IKEA對面)</strong></p><p style="text-align:center"><strong><img alt="" src="https://static.accupass.com/eventintro/2403260810146550742380.jpg" style="height:442px; width:780px"></strong></p><hr><p>注意事項&nbsp;</p><ol><li>本活動必須年滿18歲以上才可參與，<strong><span style="color:#c0392b">配合電影分級制度，必要時</span></strong>請務必配合現場工作人員進行身份查證驗，請購票觀眾務必攜帶身分證件。</li><li><strong>購票入場即可免費獲得百威金尊330ml一罐!</strong></li><li><strong>活動現場提供BBQ燻肉販售，販售方式刷卡付現皆可!</strong></li><li><strong>台北設計建材中心附設付費停車場，屋頂電影院活動及現場食物酒水消費無法進行折抵。</strong></li><li><strong>活動座位為全躺椅，入座採先來先到制，我們不提供預先劃位或保留座位。</strong></li><li>每場電影結束後我們都會進行場地整理，若您有連續購買兩場電影的觀眾可留在原座位上無需重新入場，我們的工作人員會協助您進行check in。</li><li>請注意工作人員有權請觀眾調整座位以確保每位購票者都能入席。</li><li>如遇颱⾵或其他不可抗⼒之⾃然因素導致活動必須取消/延期，我們將會透過活動通Accupass購票頁面(本頁面)及Skyline Film Facebook官方粉絲團進⾏公告。若牽涉退票事宜，我們將會按照相關法令規定，並統⼀透過活動通Accupass辦理退費。</li><li>除不可抗⼒之⾃然因素外，票卷⼀旦售出，恕不退款，若無法參加活動，請將票券轉讓其他參加⼈。</li><li>活動現場請勿吸菸。</li><li>禁止酒後駕車。</li></ol>',
        '桃園市', '中壢區中大路300號', '2024-4-1 00:00:00',
        '2024-5-15 00:00:00', '2024-4-1 00:00:00', '2024-4-28 23:59:59', '2024-4-19 00:00:00', '2024-4-28 23:59:59', 1,
        5, 1, 0),
       ('Event2', 2, 2, 'This is a description', '台北市', '信義區松壽路1號', '2024-4-2 00:00:00', '2024-5-16 00:00:00',
        '2024-4-2 00:00:00', '2024-5-16 00:00:00', '2024-5-17 06:00:00', '2024-5-17 12:00:00', 1, 5, 1, 0),
       ('Event3', 3, 3, 'This is a description', '新北市', '板橋區中山路1號', '2024-4-3 00:00:00', '2024-5-17 00:00:00',
        '2024-4-3 00:00:00', '2024-5-17 00:00:00', '2024-5-18 06:00:00', '2024-5-18 12:00:00', 1, 5, 1, 0),
       ('Event4', 4, 4, 'This is a description', '桃園市', '中壢區中大路300號', '2024-4-4 00:00:00',
        '2024-5-18 00:00:00', '2024-4-4 00:00:00', '2024-5-18 00:00:00', '2024-5-19 06:00:00', '2024-5-19 12:00:00', 1,
        5, 1, 0),
       ('Event5', 5, 5, 'This is a description', '台中市', '西區台灣大道二段2號', '2024-4-5 00:00:00',
        '2024-5-19 00:00:00', '2024-4-5 00:00:00', '2024-5-19 00:00:00', '2024-5-20 06:00:00', '2024-5-20 12:00:00', 1,
        5, 1, 0),
       ('Event6', 6, 6, 'This is a description', '台南市', '東區中華東路1段1號', '2024-4-6 00:00:00',
        '2024-5-20 00:00:00', '2024-4-6 00:00:00', '2024-5-20 00:00:00', '2024-5-21 06:00:00', '2024-5-21 12:00:00', 1,
        5, 1, 0),
       ('Event7', 7, 7, 'This is a description', '高雄市', '鹽埕區河西路1號', '2024-4-7 00:00:00', '2024-5-21 00:00:00',
        '2024-4-7 00:00:00', '2024-5-21 00:00:00', '2024-5-22 06:00:00', '2024-5-22 12:00:00', 1, 5, 1, 0),
       ('Event8', 8, 8, 'This is a description', '新竹市', '東區光復路二段101號', '2024-4-8 00:00:00',
        '2024-5-22 00:00:00', '2024-4-8 00:00:00', '2024-5-22 00:00:00', '2024-5-23 06:00:00', '2024-5-23 12:00:00', 1,
        5, 1, 0);


create table event_coupon
(
    event_coupon_no       int auto_increment
        primary key,
    host_no               int          not null,
    event_coupon_name     varchar(100) null,
    coupon_code           varchar(10)  not null,
    usage_limit           int          not null,
    remaining_times       int          null,
    min_spend             int          null,
    event_coupon_discount int          not null,
    start_time            datetime     null,
    end_time              datetime     null,
    event_coupon_status   tinyint      not null,
    coupon_desc           varchar(150) null,
    constraint event_coupon_uk
        unique (coupon_code),
    constraint event_coupon_host_host_no_fk
        foreign key (host_no) references host (host_no)
);

insert into event_coupon (host_no, event_coupon_name, coupon_code, usage_limit, remaining_times, min_spend,
                          event_coupon_discount, start_time, end_time, event_coupon_status, coupon_desc)
values (1, '創業研討會', 'ENTREP10', 100, 100, 300, 10, '2024-03-15 00:00:00', '2024-03-16 23:59:59', 1,
        '針對初創企業家的研討會，限定100次，最低消費300，享9折'),
       (2, '攝影工作坊', 'PHOTOWK20', 50, 50, 200, 20, '2024-04-10 00:00:00', '2024-04-12 23:59:59', 1,
        '攝影技巧與實踐工作坊，限定50次，最低消費200，享8折'),
       (3, '藝術展覽', 'ARTSHOW15', 70, 70, 100, 15, '2024-05-01 00:00:00', '2024-05-30 23:59:59', 1,
        '當代藝術展，限定70次，最低消費100，享85折'),
       (4, '健康與瑜伽營', 'YOGA25', 80, 80, 150, 25, '2024-06-05 00:00:00', '2024-06-10 23:59:59', 1,
        '結合健康飲食與瑜伽的營，限定80次，最低消費150，享75折'),
       (5, '程式開發大會', 'CODECONF20', 150, 150, 400, 20, '2024-07-20 00:00:00', '2024-07-22 23:59:59', 1,
        '面向軟體開發者的大會，限定150次，最低消費400，享8折'),
       (6, '創新教育論壇', 'EDUINNO15', 120, 120, 250, 15, '2024-08-15 00:00:00', '2024-08-17 23:59:59', 1,
        '探討教育創新的論壇，限定120次，最低消費250，享85折'),
       (7, '美食節', 'FOODFEST20', 200, 200, 100, 20, '2024-09-10 00:00:00', '2024-09-12 23:59:59', 1,
        '展示各地美食的節日，限定200次，最低消費100，享8折'),
       (8, '時尚週', 'FASHWK30', 100, 100, 300, 30, '2024-10-05 00:00:00', '2024-10-10 23:59:59', 1,
        '最新時尚趨勢發布週，限定100次，最低消費300，享7折'),
       (9, '電影節', 'FILMFEST25', 150, 150, 200, 25, '2024-11-15 00:00:00', '2024-11-20 23:59:59', 1,
        '國際電影展映活動，限定150次，最低消費200，享75折'),
       (10, '聖誕市集', 'XMAS15', 80, 80, 50, 15, '2024-12-01 00:00:00', '2024-12-25 23:59:59', 1,
        '聖誕節慶活動與市集，限定80次，最低消費50，享85折');


create table member
(
    member_no                int auto_increment
        primary key,
    member_mail              varchar(50)  not null,
    member_pwd               varchar(20)  not null,
    member_name              varchar(50)  null,
    birthday                 date         null,
    gender                   tinyint      null,
    member_points            int     default 0,
    member_phone             varchar(15)  null,
    address                  varchar(200) null,
    common_recipient         varchar(50)  null,
    common_recipient_phone   varchar(15)  null,
    common_recipient_address varchar(200) null,
    member_status            tinyint default 0,
    constraint member_uk1
        unique (member_mail),
    constraint member_uk2
        unique (member_phone)
);

INSERT INTO member (member_mail, member_pwd, member_name, birthday, gender, member_points, member_phone, address,
                    member_status)
VALUES ('member1@gmail.com', 'password1', 'Member1', '2000-01-01', 1, 100, '0900000001', 'Address1', 1),
       ('member2@gmail.com', 'password2', 'Member2', '2000-02-01', 0, 100, '0900000002', 'Address2', 1),
       ('member3@gmail.com', 'password3', 'Member3', '2000-03-01', 1, 100, '0900000003', 'Address3', 1),
       ('member4@gmail.com', 'password4', 'Member4', '2000-04-01', 0, 100, '0900000004', 'Address4', 1),
       ('member5@gmail.com', 'password5', 'Member5', '2000-05-01', 1, 100, '0900000005', 'Address5', 1),
       ('member6@gmail.com', 'password6', 'Member6', '2000-06-01', 0, 100, '0900000006', 'Address6', 1),
       ('member7@gmail.com', 'password7', 'Member7', '2000-07-01', 1, 100, '0900000007', 'Address7', 1),
       ('member8@gmail.com', 'password8', 'Member8', '2000-08-01', 0, 100, '0900000008', 'Address8', 1),
       ('member9@gmail.com', 'password9', 'Member9', '2000-09-01', 1, 100, '0900000009', 'Address9', 1),
       ('member10@gmail.com', 'password10', 'Member10', '2000-10-01', 0, 100, '0900000010', 'Address10', 1);


create table birthday_coupon_holder
(
    birthday_coupon_holder_no int      not null Auto_Increment primary key,
    member_no                 int      not null,
    birthday_coupon_no        int      not null,
    coupon_usage_status       tinyint  not null,
    validity_date             datetime not null,
    foreign key (birthday_coupon_no) references birthday_coupon (birthday_coupon_no),
    constraint birthday_coupon_holder_member_member_no_fk
        foreign key (member_no) references member (member_no)
);

create table event_comment
(
    event_comment_no      int auto_increment
        primary key,
    event_no              int      not null,
    member_no             int      not null,
    event_comment_rate    int      null,
    event_comment_content text     null,
    event_comment_time    datetime null,
    event_comment_status  tinyint  null,
    constraint event_comment_event_event_no_fk
        foreign key (event_no) references event (event_no),
    constraint event_comment_member_member_no_fk
        foreign key (member_no) references member (member_no)
);

INSERT INTO event_comment (event_no, member_no, event_comment_rate, event_comment_content, event_comment_time,
                           event_comment_status)
VALUES (1, 1, 5, 'This is a comment', NOW(), 1);


create table event_comment_report
(
    event_comment_report_no int auto_increment
        primary key,
    event_comment_no        int      not null,
    member_no               int      not null,
    admin_no                int      null,
    report_reason           text     null,
    report_time             datetime not null,
    report_status           tinyint  not null,
    constraint event_comment_report_admin_admin_no_fk
        foreign key (admin_no) references admin (admin_no),
    constraint event_comment_report_event_comment_event_comment_no_fk
        foreign key (event_comment_no) references event_comment (event_comment_no),
    constraint event_comment_report_member_member_no_fk
        foreign key (member_no) references member (member_no)
);

INSERT INTO event_comment_report (event_comment_no, member_no, report_reason, report_time, report_status)
VALUES (1, 1, 'This is a reason', NOW(), 1);


create table notification
(
    notification_no      int auto_increment
        primary key,
    member_no            int          null,
    host_no              int          null,
    admin_no             int          null,
    notification_content varchar(200) null,
    read_status          tinyint      null,
    notification_time    datetime     null,
    constraint notification_host_host_no_fk
        foreign key (host_no) references host (host_no),
    constraint notification_member_member_no_fk
        foreign key (member_no) references member (member_no)
);

INSERT INTO notification (member_no, host_no, admin_no, notification_content, read_status, notification_time)
VALUES (1, 1, 1, 'This is a notification', 0, NOW());


create table points_record
(
    points_record_no int auto_increment
        primary key,
    member_no        int      not null,
    points_changed   int      not null,
    transaction_time datetime null,
    constraint points_record_member_member_no_fk
        foreign key (member_no) references member (member_no)
);

INSERT INTO points_record (member_no, points_changed, transaction_time)
VALUES (1, 10, NOW());


create table qa
(
    qa_no      int auto_increment
        primary key,
    qa_title   varchar(50) null,
    qa_content longtext    null
);

INSERT INTO qa (qa_title, qa_content)
VALUES ('Question1', 'This is a question');


create table save_event
(
    save_event_no int not null auto_increment primary key ,
    member_no int not null,
    event_no  int not null,
    constraint save_event_event_event_no_fk
        foreign key (event_no) references event (event_no),
    constraint save_event_member_member_no_fk
        foreign key (member_no) references member (member_no)
);

INSERT INTO save_event (member_no, event_no)
VALUES (1, 1);


create table ticket_order
(
    ticket_order_no                int auto_increment
        primary key,
    member_no                      int      not null,
    event_coupon_no                int      null,
    ticket_order_amount            int      not null,
    event_coupon_discount          int      null,
    ticket_checkout_amount         int      not null,
    ticket_order_time              datetime not null,
    ticket_order_pay_time          datetime null,
    ticket_order_payment_status    tinyint  not null default 0,
    ticket_order_status            int      null,
    ticket_order_allocation_amount int      null,
    ticket_order_allocation_status int      null,
    constraint ticket_order_event_coupon_event_coupon_no_fk
        foreign key (event_coupon_no) references event_coupon (event_coupon_no),
    constraint ticket_order_member_member_no_fk
        foreign key (member_no) references member (member_no)
);

INSERT INTO ticket_order (member_no, event_coupon_no, ticket_order_amount, event_coupon_discount,
                          ticket_checkout_amount, ticket_order_time,
                          ticket_order_pay_time, ticket_order_payment_status)
VALUES (1, 1, 100, 10, 90, '2024-3-1 20:00:00', '2024-3-1 20:05:00', 3);


create table ticket_type
(
    ticket_type_no       int auto_increment
        primary key,
    event_no             int           not null,
    ticket_type_name     varchar(50)   not null,
    included_tickets     int default 1 not null,
    purchase_start_time  datetime      not null,
    purchase_end_time    datetime      not null,
    ticket_type_info     varchar(200)  null,
    ticket_type_qty      int           not null,
    remaining_ticket_qty int           null,
    constraint ticket_type_fk
        foreign key (event_no) references event (event_no)
);

INSERT INTO ticket_type (event_no, ticket_type_name, included_tickets, purchase_start_time, purchase_end_time,
                         ticket_type_info, ticket_type_qty)
VALUES (1, '單人票', 1, NOW(), NOW(), 'This is a ticket type', 100),
       (1, '雙人票', 2, NOW(), NOW(), 'This is a ticket type', 100),
       (1, '四人套票', 4, NOW(), NOW(), 'This is a ticket type', 100),
       (2, '單人票', 1, NOW(), NOW(), 'This is a ticket type', 100),
       (2, '雙人票', 2, NOW(), NOW(), 'This is a ticket type', 100),
       (2, '四人套票', 4, NOW(), NOW(), 'This is a ticket type', 100),
       (3, '單人票', 1, NOW(), NOW(), 'This is a ticket type', 100),
       (3, '雙人票', 2, NOW(), NOW(), 'This is a ticket type', 100),
       (3, '四人套票', 4, NOW(), NOW(), 'This is a ticket type', 100),
       (4, '單人票', 1, NOW(), NOW(), 'This is a ticket type', 100),
       (4, '雙人票', 2, NOW(), NOW(), 'This is a ticket type', 100),
       (4, '四人套票', 4, NOW(), NOW(), 'This is a ticket type', 100),
       (5, '單人票', 1, NOW(), NOW(), 'This is a ticket type', 100),
       (5, '雙人票', 2, NOW(), NOW(), 'This is a ticket type', 100),
       (5, '四人套票', 4, NOW(), NOW(), 'This is a ticket type', 100),
       (6, '單人票', 1, NOW(), NOW(), 'This is a ticket type', 100),
       (6, '雙人票', 2, NOW(), NOW(), 'This is a ticket type', 100),
       (6, '四人套票', 4, NOW(), NOW(), 'This is a ticket type', 100),
       (7, '單人票', 1, NOW(), NOW(), 'This is a ticket type', 100),
       (7, '雙人票', 2, NOW(), NOW(), 'This is a ticket type', 100),
       (7, '四人套票', 4, NOW(), NOW(), 'This is a ticket type', 100),
       (8, '單人票', 1, NOW(), NOW(), 'This is a ticket type', 100),
       (8, '雙人票', 2, NOW(), NOW(), 'This is a ticket type', 100),
       (8, '四人套票', 4, NOW(), NOW(), 'This is a ticket type', 100);



create table ticket_order_detail
(
    ticket_order_detail_no int auto_increment
        primary key,
    ticket_type_no         int not null,
    ticket_order_no        int not null,
    ticket_type_price      int not null,
    ticket_type_qty        int not null,
    included_ticket_qty    int not null,
    constraint ticket_order_detail_ticket_order_ticket_order_no_fk
        foreign key (ticket_order_no) references ticket_order (ticket_order_no),
    constraint ticket_order_detail_ticket_type_ticket_type_no_fk
        foreign key (ticket_type_no) references ticket_type (ticket_type_no)
);

INSERT INTO ticket_order_detail (ticket_type_no, ticket_order_no, ticket_type_price, ticket_type_qty,
                                 included_ticket_qty)
VALUES (1, 1, 10, 10, 1);


create table qrcode_ticket
(
    ticket_no              bigint auto_increment
        primary key,
    ticket_order_detail_no int      not null,
    member_no              int      not null,
    ticket_usage_status    tinyint  null,
    ticket_valid_time      datetime not null,
    constraint qrcode_ticket_member_member_no_fk
        foreign key (member_no) references member (member_no),
    constraint qrcode_ticket_ticket_order_detail_ticket_order_detail_no_fk
        foreign key (ticket_order_detail_no) references ticket_order_detail (ticket_order_detail_no)
);

INSERT INTO qrcode_ticket (ticket_order_detail_no, member_no, ticket_usage_status, ticket_valid_time)
VALUES (1, 1, 0, NOW());


CREATE TABLE product_category
(
    product_category_no   INT NOT NULL AUTO_INCREMENT,
    product_category_name VARCHAR(50),
    CONSTRAINT product_category_no_pk PRIMARY KEY (product_category_no)
);

INSERT INTO product_category (product_category_name)
VALUES ('Category1'),
       ('Category2'),
       ('Category3'),
       ('Category4'),
       ('Category5');

create table product
(
    product_no           int auto_increment,
    product_category_no  int         null,
    product_name         varchar(20) null,
    host_no              int         not null,
    product_desc         text        null,
    product_price        int         not null,
    product_add_qty      int         not null,
    remaining_qty        int         null,
    product_add__time    datetime    null,
    product_remove_time  datetime    null,
    product_status       tinyint     not null,
    product_total_rating int         null,
    product_rating_count int         null,
    constraint product_pk
        primary key (product_no),
    constraint product_host_host_no_fk
        foreign key (host_no) references host (host_no),
    constraint product_product_category_product_category_no_fk
        foreign key (product_category_no) references product_category (product_category_no)
);

INSERT INTO product (product_category_no, product_name, host_no, product_desc, product_price, product_add_qty,
                     product_status, product_total_rating, product_rating_count)
VALUES (1, 'Product1', 1, 'This is a product description', 100, 10, 1, 5, 1),
       (2, 'Product2', 1, 'This is a product description', 200, 20, 1, 5, 1),
       (3, 'Product3', 2, 'This is a product description', 300, 30, 1, 5, 1),
       (4, 'Product4', 2, 'This is a product description', 400, 40, 1, 5, 1),
       (5, 'Product5', 3, 'This is a product description', 500, 50, 1, 5, 1);

create table product_comment
(
    product_comment_no      int auto_increment
        primary key,
    product_no              int      not null,
    member_no               int      not null,
    product_rate            int      not null,
    product_comment_content text     null,
    product_comment_date    datetime not null,
    product_comment_status  tinyint  not null,
    constraint product_comment_member_member_no_fk
        foreign key (member_no) references member (member_no),
    constraint product_comment_product_product_no_fk
        foreign key (product_no) references product (product_no)
);

create table product_comment_report
(
    product_comment_report_no int auto_increment
        primary key,
    product_comment_no        int          not null,
    member_no                 int          not null,
    admin_no                  int          null,
    report_reason             varchar(100) not null,
    report_date               datetime     not null,
    report_status             tinyint      not null,
    constraint product_comment_report_admin_admin_no_fk
        foreign key (admin_no) references admin (admin_no),
    constraint product_comment_report_member_member_no_fk
        foreign key (member_no) references member (member_no),
    constraint product_comment_report_product_comment_product_comment_no_fk
        foreign key (product_comment_no) references product_comment (product_comment_no)
);



CREATE TABLE product_img
(
    product_img_no INT NOT NULL AUTO_INCREMENT,
    product_no     INT,
    product_img    LONGBLOB,
    CONSTRAINT product_img_no_pk PRIMARY KEY (product_img_no),
    CONSTRAINT product_img_product_no_fk FOREIGN KEY (product_no) REFERENCES product (product_no)
);

create table product_report
(
    product_report_no int auto_increment
        primary key,
    product_no        int          not null,
    member_no         int          not null,
    admin_no          int          null,
    report_reason     varchar(100) not null,
    report_date       datetime     not null,
    report_status     tinyint      not null,
    constraint product_report_admin_admin_no_fk
        foreign key (admin_no) references admin (admin_no),
    constraint product_report_member_member_no_fk
        foreign key (member_no) references member (member_no),
    constraint product_report_product_product_no_fk
        foreign key (product_no) references product (product_no)
);

insert into product_report (product_no, member_no, admin_no, report_reason, report_date, report_status)
values (1, 1, 1, '內有不雅字眼', now(), 0),
       (2, 1, 2, '價格不合理', now(), 1),
       (3, 2, 3, '商品名稱太難聽', now(), 1),
       (4, 3, 4, '我完全用不到', now(), 1),
       (5, 3, 5, '就是想檢舉這樣商品', now(), 0);

create table product_order
(
    product_order_no                int primary key auto_increment not null,
    member_no                       int                            not null,
    product_price                   int                            not null,
    product_coupon_discount         int      default 0,
    product_checkout_amount         int                            not null,
    member_points                   int      default 0,
    birthday_coupon_no              int      default null,
    recipient                       varchar(50)                    not null,
    recipient_phone                 varchar(15)                    not null,
    recipient_address               varchar(200)                   not null,
    product_orderdate               dateTime default now(),
    product_paydate                 dateTime default now(),
    order_closedate                 dateTime default null,
    product_payment_status          tinyint                        not null,
    product_process_status          tinyint                        not null,
    product_order_allocation_amount int                            not null,
    product_order_allocation_status tinyint                        not null,
    foreign key (member_no) references member (member_no),
    foreign key (birthday_coupon_no) references birthday_coupon (birthday_coupon_no)
)auto_increment = 1001;

insert into product_order (member_no, product_price, product_coupon_discount, product_checkout_amount, member_points,
                           birthday_coupon_no, recipient, recipient_phone, recipient_address, product_orderdate,
                           product_paydate, order_closedate, product_payment_status, product_process_status,
                           product_order_allocation_amount, product_order_allocation_status)
values (1, 5000, null, 5000, null, null, '葉先森', '0912345678', '桃園市中壢區11號', now(), now(), null, 1, 4, 4950, 1),
       (2, 4000, null, 3950, 50, null, '陳曉捷', '0922345678', '桃園市中壢區12號', now(), now(), null, 1, 2, 3950, 0),
       (3, 3000, null, 2900, 100, null, '余鮮聲', '0932345678', '桃園市中壢區13號', now(), now(), null, 1, 0, 2950, 0),
       (4, 2000, 100, 1900, null, 1, '黃仙聲', '0942345678', '桃園市中壢區14號', now(), now(), null, 1, 2, 1950, 1),
       (5, 1000, 100, 900, null, 1, '王子森', '0952345678', '桃園市中壢區15號', now(), now(), null, 1, 0, 950, 0);


create table product_order_detail
(
    product_order_detail_no int     not null AUTO_INCREMENT PRIMARY KEY,
    product_no              int     not null,
    product_order_no        int     not null,
    product_qty             int     not null,
    product_price           int     not null,
    comments_status         tinyint not null,
    foreign key (product_no) references product (product_no),
    foreign key (product_order_no) references product_order (product_order_no)
);
insert into product_order_detail (product_no, product_order_no, product_qty, product_price, comments_status)
values (1, 1001, 4, 5000, 0),
       (2, 1002, 4, 3950, 1),
       (3, 1003, 2, 2900, 1),
       (4, 1004, 3, 1900, 0),
       (5, 1005, 2, 900, 0);

create table save_product
(
    save_product_no int not null auto_increment primary key ,
    member_no  int not null,
    product_no int not null,
    foreign key (member_no) references member (member_no),
    foreign key (product_no) references product (product_no)
);
insert into save_product (member_no, product_no)
values (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5);