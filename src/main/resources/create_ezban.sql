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
    admin_account varchar(20) not null,
    admin_pwd     varchar(60) not null,
    admin_name    varchar(50) null,
    admin_mail    varchar(50) null,
    admin_phone   varchar(15) null,
    admin_status  tinyint     null,
    admin_login   DATETIME    NULL,
    constraint admin_uk_1
        unique (admin_account),
    constraint admin_uk_2
        unique (admin_mail),
    constraint admin_uk_3
        unique (admin_phone)
);

INSERT INTO admin (admin_account, admin_pwd, admin_mail, admin_phone, admin_status, admin_name)

VALUES
    ( 'AY6KQZZh', 'NkzkVJ2JUH', 'u8yzliwj@gmail.com', '0989599470', 1,'陳昊天'),
    ( 'mE1rur8H', 'cR7eCn9dEd8rISQ', 'yeocffh6b3wj@gmail.com', '0980469290', 1, '王晨曦'),
    ( 'mX14vJUo7MYQA', 'DsPLWXZ8jKoEz', 'pygvd8xpeex8vbf@gmail.com', '0926584626', 1, '張宇宸'),
    ( 'dFOYfKRWrM', 'vOYvKXCsOlan', '6otfkc9jzl@gmail.com', '0954426431', 1, '李雅琳'),
    ( 'oAFx5V1I', 'e3ez2mANzGY', 'ns9sxzx15rzuv@gmail.com', '0992857410', 1, '劉悅心'),
    ( 'hSUhWbw3', 'BR8ivTB5p', '8avtoze3glac@gmail.com', '0990057569', 1, '黃宇航'),
    ( '6KGLkBq8T', '0Bzc2bJr', 'lf4v6j4bivrokr@gmail.com', '0932655759', 1, '蔡欣怡'),
    ( 'cznsnaRVakuaAoV', 'ZT4TMNkhLeLfuF2', 'g6gdfowqi4gpkqy@gmail.com', '0906019527', 1, '林啟航'),
    ( 'oy8EgoOTY2q', 'JR72fjeSNKYnX', 'zes7tkfl9dkmzub@gmail.com', '0942943961', 1, '陳心悅'),
    ( 'lE6XihvE', 'BbCqJsKgu', 'vbrdpx7u4qjv0@gmail.com', '0901244156', 1, '鄭晨星');


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
values (50, 0, 30),
       (50, 1, 30),
       (50, 2, 30),
       (50, 0, 30),
       (50, 2, 30);

INSERT INTO event_category (event_category_name)
VALUES ('路跑'),
       ('音樂會'),
       ('研討會'),
       ('展覽'),
       ('運動'),
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
VALUES ('Manager'),
       ('Product activities'),
       ('Order_management'),
       ('Birthday_management'),
       ('Content_management');


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
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5);



create table host
(
    host_no      int auto_increment
        primary key,
    host_account varchar(20) null,
    host_pwd     varchar(60) null,
    host_name    varchar(50) null,
    host_mail    varchar(50) null,
    host_phone   varchar(15) null,
    host_status  tinyint     null,
    host_login   DATETIME null,

    constraint host_uk_1
        unique (host_account),
    constraint host_uk_2
        unique (host_mail),
    constraint host_uk_3
        unique (host_phone)
);

INSERT INTO host (host_account, host_pwd, host_name, host_mail, host_phone, host_status)
VALUES ('host001', 'h123321', '星光傳媒有限公司', 'alexander23@gmail.com', '0912345678', 1),
       ('host002', 'h456654', '璀璨文化傳媒集團', 'emily.smith@gmail.com', '0928765432', 1),
       ('host003', 'h789987', '綠葉活動策劃有限公司', 'samuel1987@gmail.com', '0934567890', 1),
       ('host004', 'h111222', '千禧時代活動策劃有限公司', 'sophie.jones@gmail.com', '0987654321', 1),
       ('host005', 'h333444', '優客活動組織有限公司', 'maxwell45@gmail.com', '0956123456', 1),
       ('host006', 'h555666', '盛世傳媒活動策劃公司', 'olivia.taylor@gmail.com', '0978654321', 1),
       ('host007', 'h777888', '美好時光文化傳媒有限公司', 'ethan.miller@gmail.com', '0903210987', 1),
       ('host008', 'h090909', '藍天文化傳媒有限公司', 'isabella88@gmail.com', '0965432109', 1),
       ('host009', 'h123456', '珍珠文化傳媒有限公司', 'nathan.parker@gmail.com.com', '0921098765', 1),
       ('host010', 'h654321', '維爾德國際活動策劃公司', 'ava.roberts@gmail.com', '0943210987', 1);



create table event
(
    event_no               int auto_increment
        primary key,
    event_img              longblob     null,
    event_name             varchar(50)  not null,
    event_category_no      int          null,
    host_no                int          not null,
    event_desc             longtext     null,
    event_city             varchar(15)  null,
    event_detailed_address varchar(250) null,
    event_add_time         datetime     null,
    event_remove_time      datetime     null,
    event_start_time       datetime     not null,
    event_end_time         datetime     not null,
    registered_count       int          null default 0,
    visit_count            int       null default 0,
    event_status           tinyint      not null,
    total_rating           int          null,
    event_rating_count     int          null,
    event_checkout_status  tinyint      null,
    event_checkout_time    int          null,
    constraint event_event_category_event_category_no_fk
        foreign key (event_category_no) references event_category (event_category_no),
    constraint event_host_host_no_fk
        foreign key (host_no) references host (host_no)
);

INSERT INTO event (event_name, event_category_no, host_no, event_desc, event_city, event_detailed_address,
                   event_add_time, event_remove_time, event_start_time,
                   event_end_time, event_status, total_rating, event_rating_count, event_checkout_status,visit_count)
VALUES ('Skyline Film 屋頂電影院', 7, 1,
        '<p><strong>台北好久不見!</strong></p><p><strong>這次4月在內湖的屋頂放映，連續兩週，十部電影，不變的經典無線耳機與木質躺椅，遠眺著小半個台北盆地，春日徐徐微風下，人手一杯啤酒與美式燻肉，暢快舒適的屋頂體驗，限定回歸!</strong></p><p><strong>4/19(Fri)-4/21(Sun)及4/26-4/28(Sun) 這兩週，我們刻意把週五的場次縮減為一場，將整體時間延後至8:30開演，下班下課的你們可以不用那麼趕了!</strong></p><p><strong>快把時間留下來，來屋頂找我們喝一杯看電影!</strong></p><p>&nbsp;</p><p><span style="color:#c0392b"><strong>凡購票入場觀眾即可免費獲得百威金尊330ml一罐!</strong></span></p><p><strong>期待天氣很好，我們4月內湖台北建材中心屋頂見!</strong></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403260546501777605381.jpg" style="height:400px; width:600px"></p><p style="text-align:center"><strong><img alt="" src="https://static.accupass.com/eventintro/2305190800441741697587.jpg" style="height:400px; width:600px"></strong></p><hr><p><strong>4/19(Fri)</strong><br><strong>20:30 Forrest Gump 阿甘正傳&nbsp;(19:30 開放入場 Open at 19:30)</strong><br><br><span style="color:#999999">阿甘坐在公園的長凳上，向往來的人說著自己的故事。</span></p><p><span style="color:#999999">他是美式足球明星、越戰英雄、乒乓球國家隊，現在更是個成功的生意人。</span></p><p><span style="color:#999999">但如果沒有母親和Jenny，他這輩子惦記最深的兩個人，阿甘或許不會有那麽不平凡的人生。</span></p><p><span style="color:#999999">而他的故事，要從那個腦袋不太靈光，肢體天生有些缺陷的孩子說起.....</span></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403250844331285794200.jpg" style="height:402px; width:600px"></p><p>&nbsp;</p><hr><p><strong>4/20(Sat) </strong></p><p><strong>18:30 In Bruges&nbsp;</strong><strong>殺手沒有假期</strong><strong> (17:30</strong><strong>開放入場</strong><strong> Open at 17:30)</strong></p><p><span style="color:#999999">Ken和Ray在完成了刺殺任務後，奉命來到比利時的布魯日度假。</span></p><p><span style="color:#999999">在充滿觀光客的中世紀小鎮裡，他倆參訪古蹟、看看藝術展覽與畫作，雖然挺輕鬆的，但怎麼樣都不像是兩個殺手喜歡待的地方。</span></p><p><span style="color:#999999">原來Ray在上次的刺殺任務中，雖然成功消滅目標，卻也誤殺了一位無辜的小男孩，而Ken也突然接到了上級的新任務，就是要殺了Ray......</span></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403250852257420067220.jpg" style="height:420px; width:600px"></p><p><strong>21:20 The Boat That Rocked&nbsp;海盜電台</strong><strong>&nbsp;(20:40</strong><strong>開放入場</strong><strong> Open at 20:40)</strong></p><p><span style="color:#999999">在英國60年代，人們唯一能夠聽到搖滾樂的地方，是一艘大西洋飄盪的破船 ── 一個在海上發送違法電波、百無禁忌的海盜電台。<br><br>但到了上了船之後，才知道這群改變世界的不法之徒，其實只是一群被貼上各種標籤的怪胎，用著生命愛著搖滾樂。<br><br>別忘了，搖滾樂只有一個規則，就是沒有規則。</span></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403250856291296833566.jpg" style="height:399px; width:600px"></p><hr><p><strong>4/21(Sun) </strong></p><p><strong>18:30 De Surpirse&nbsp;意外製造公司</strong><strong>&nbsp;(17:30 </strong><strong>開放入場</strong><strong> Open at 17:30)</strong></p><p><span style="color:#999999">歐洲西部的一座貴族莊園，雅各冷漠地看著母親死去，他心中早已對生命失去熱情，捨棄了一切，只想要找一個平靜而無痛的死法。</span></p><p><span style="color:#999999">一次偶然的機會，他發現了這間提供人生單程票的公司，在選購了不知道死法，前往極樂世界的「驚喜包」的旅程後，他卻遇到了唯一讓他動心的旅伴…</span></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403250902237384089740.jpg" style="height:337px; width:600px"></p><p><strong>21:15&nbsp;The Lunchbox&nbsp;美味情書</strong><strong>&nbsp;(20:35&nbsp;</strong><strong>開放入場</strong><strong> Open at 20:35)</strong></p><p><span style="color:#999999">百年來，孟買的火車便當快遞乎從不出錯。</span></p><p><span style="color:#999999">IIa今天特別開心，因為他親手為丈夫做的午餐便當吃得精光。她期望著透過親手製作的美味便當，挽回兩人日漸消散的感情。</span></p><p><span style="color:#999999">但與丈夫詢問後，IIa發現他的便當居然被送到了陌生人嘴裡。她接連送了幾次，發現便當從來沒有送到丈夫那兒，但每天卻都被吃的乾乾淨淨...</span></p><p><span style="color:#999999">IIa雖然對於送錯便當感到失望，但卻想對這個每天吃光她便當的陌生人說聲感謝，於是她留下了紙條在便當內。</span></p><p><span style="color:#999999">傍晚，IIa收到了來自Saajan的第一封回信......</span></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403260505041518195971.jpg" style="height:345px; width:600px"></p><hr><p><strong>4/26(Fri) </strong></p><p><strong>20:30 Nuovo Cinema Paradiso 新天堂樂園&nbsp;(19:30 </strong><strong>開放入場</strong><strong> Open at 19:30)</strong></p><p><span style="color:#999999">多年以後，當頑童Toto成為大導演Salvatore之後，他才回到一切開始的地方—早已荒廢的「天堂樂園電影院」。<br><br>曾經，「天堂樂園電影院」是世界上最棒的地方。因為那裡有電影、有笑聲，還有一個在放映室，守候著鎮民笑容的年老放映師Adelfio。Toto的電影夢在這裡萌芽，但也為了夢想，他必須離開家鄉...<br><br>記得每一次看電影的感動，而這一次，將會更深刻...</span></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403260511115616575040.jpg" style="height:312px; width:600px"></p><hr><p><strong>4/27(Sat)</strong></p><p><strong>18:30 Love Letter&nbsp;</strong><strong>情書</strong><strong> (17:30</strong><strong>開放入場</strong><strong> Open at 17:30) </strong></p><p><span style="color:#999999">渡邊博子的未婚夫藤井樹因山難過世。她的哀傷、憤怒、思念與愛，都像一封無法投遞的信一樣，壓得她無法呼吸。<br><br>半開玩笑地，她找出了藤井樹高中的畢業紀念冊，「你好嗎？我很好……」博子照著上頭的地址寫了信給他。然後，她收到了藤井樹的回信。早已拆遷的老家、早已死去的人，早在多年前結束的故事，因此重新轉動了起來……</span></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403260514019388401890.jpg" style="height:251px; width:600px"></p><p><strong>21:30 &nbsp;Seven Psychopaths</strong><strong> 瘋狗綁票令&nbsp;(20:50</strong><strong>開放入場</strong><strong> Open at 20:50) </strong></p><p><span style="color:#999999">Marty是個編劇，他想好了新劇本的標題 - Seven Psychopaths，正苦惱著內容該如何動筆。</span></p><p><span style="color:#999999">這天，無業的演員好友Billy來訪，談論到他與另一位老人Hans正在做的工作 - 職業狗狗綁匪，而好巧不巧，他們新的受害對象，正是黑道大哥Charlie最疼愛的小西施...</span></p><p><span style="color:#999999">而Marty也成為這場無厘頭綁票的受害者，四人一狗與他們周遭一切的相愛相殺，就此展開。</span></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403260517228821475190.jpg" style="height:400px; width:600px"></p><hr><p><strong>4/28(Sun)</strong></p><p><strong>18:30 The Worst Person in the World</strong><strong> 世界上最爛的人&nbsp;(17:30</strong><strong>開放入場</strong><strong> Open at 17:30) </strong></p><p><span style="color:#999999">年輕的你，應該適合矛盾與徬徨吧?&nbsp;</span></p><p><span style="color:#999999">Julie是個聰明、自信且充滿行動力的人，在學業、職涯與感情間，她總是投其所好，但也會果斷的踩下煞車。</span></p><p><span style="color:#999999">在看似打破傳統框架下的自在人生，Julie仍舊活得像是迷途鳥兒一般，拍打著翅膀，卻不知道目的地......</span></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403260521061977443713.jpg" style="height:401px; width:600px"></p><p>&nbsp;</p><p><strong>21:30 Annie Hall</strong><strong>&nbsp;安妮霍爾&nbsp;(20:50</strong><strong>開放入場</strong><strong> Open at 20:50) </strong></p><p><span style="color:#999999">Alvy Singer fall in love with Annie Hall。</span></p><p><span style="color:#999999">這就是全部的故事，就是全部的甜蜜、浪漫、悲傷與悔恨。</span></p><p><span style="color:#999999">簡單而複雜，因為Alvy是一個矛盾的人，他是一個憂傷不得志的喜劇演員，他需要愛卻又尖酸刻薄，他神經質又自以為是。是一個充滿缺點但無法讓人討厭的人，因為懦弱的他，如此勇敢的愛，畏縮的他，如此真誠的坦率。</span></p><p><span style="color:#999999">男人的脆弱，還有女人的美，以幽默掩飾安全感...Woody Allen是沒在客氣的!</span></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403260547311383970679.jpg" style="height:338px; width:600px"></p><hr><p><strong>Skyline 屋頂限定</strong></p><p>Skyline Film 與每一位合作夥伴在屋頂，帶給你們最特別的體驗。</p><p><strong><span style="color:#c0392b">由爅登煙醺在屋頂提供的各類不同菜單(燻肉產品無包含在票價中，需額外購買)</span></strong>，以原木慢火柴燒，軟嫩夠味的手撕豬與牛前胸，香嫩帶骨的豬肋排，配上夠味的墨西哥辣椒、酸黃瓜與生洋蔥末，最後淋上秘制香料BBQ醬，來屋頂就是可以這麼享受!</p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2302200818024123072500.jpg" style="height:400px; width:600px"></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2302200818501253558762.jpg" style="height:400px; width:600px"></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2305190701221976350000.jpg" style="height:446px; width:600px"></p><p>&nbsp;</p><p><strong>1.柴燒煙燻牛前胸肉+煙燻香腸或培根.搭配墨西哥辣椒.酸黃瓜.生洋蔥.特製B.B.Q.醬及餐包</strong> <span style="color:#c0392b"><strong>$350</strong></span></p><p><strong>2.柴燒煙燻手撕豬肉+煙燻香腸或培根搭配墨西哥辣椒. 酸黃瓜.生洋蔥.特製B.B.Q.醬及餐包<span style="color:#c0392b"> $280</span></strong></p><p><strong>3.柴燒煙燻雞腿+煙燻香腸或培根搭配墨西哥辣椒. 酸黃瓜.生洋蔥.特製B.B.Q.醬及餐包<span style="color:#c0392b"> $280</span></strong></p><p><strong>4.雙人分享餐[手撕豬]+[牛前胸]or[雞腿](2選1)&nbsp;+煙燻香腸或培根搭配墨西哥辣椒. 酸黃瓜.生洋蔥.特製B.B.Q.醬及餐包</strong> <span style="color:#c0392b"><strong>$600</strong></span></p><p><strong>5.柴燒煙燻分享派對燻肉(牛前胸.雞腿.手撕豬.適合3-4人)+煙燻香腸或培根搭配墨西哥辣椒. 酸黃瓜.生洋蔥.特製B.B.Q.醬及餐包</strong> <span style="color:#c0392b"><strong>$900</strong></span></p><p><strong>6. 煙燻手撕豬肉捲餅 <span style="color:#c0392b">$150 (僅提供第二場次)</span></strong></p><p><strong>7.煙燻雞腿捲餅 <span style="color:#c0392b">$150 (僅提供第二場次)</span></strong></p><p><strong>8. 煙燻牛前胸捲餅 <span style="color:#c0392b">$200 (僅提供第二場次)</span></strong></p><p><strong><span style="color:#c0392b">無論是一個人、兩個人，還是一群人，屋頂上的肉肉party，我們通通一網打盡!&nbsp;</span></strong></p><hr><p>購票即免費贈送最具王者風範的<strong>百威金尊一罐，單一品種麥芽啤酒的純正血統，純粹豐郁口感不只是絕妙的搭餐酒，百威金尊就像是皇冠上的珍珠，是屋頂電影院絕對不能少的心頭好。來屋頂用百威金尊，搭配精緻燻肉，悠閒的下班與週末時光，就該這樣過!</strong></p><p><strong>此外，百威金尊攤位每場次都將提供限量拍照打卡禮，快來小巧精美的金尊Stand bar找我們玩!</strong></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2302130555461514491948.jpg" style="height:338px; width:600px"></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2305190703379459226790.jpg" style="height:400px; width:600px"></p><hr><p><strong><span style="background-color:#f39c12">yoxi x&nbsp;Skyline Film&nbsp;屋頂電影院&nbsp;乘車專案</span></strong></p><p>內湖不方便? 電影結束怕沒有捷運? 想喝酒又怕回不了家?<br>yoxi攜手屋頂電影院推出多樣乘車專屬優惠，讓我們來解決你的交通煩惱!</p><p><strong><span style="color:#c0392b">1.&nbsp;&nbsp;新朋友限定：</span></strong> <span style="color:#c0392b">新用戶首次搭乘前輸入 [ skf2024 ］即可兌換$200優惠 （折抵面額為$50*4）</span>。新朋友優惠領取截止日為4/29 ，領取後60天內可搭乘使用。</p><p><strong><span style="color:#c0392b">2. 新朋友老朋友一起用：</span></strong> <span style="color:#c0392b">不分新舊用戶，只要於4/5-4/29活動宣傳期間，輸入[ 屋頂電影夜］預約叫車享2趟9折優惠，單趟最高折抵 $100</span>，領取後30天內可搭乘使用!</p><p><strong><span style="color:#c0392b">3. 放映期間限定:</span></strong> 不分新舊用戶，我們的工作人員將於每場次電影開演前公布現時神秘優惠碼，現場的觀眾們要好好把握機會啦!</p><p><strong>p.s.&nbsp;yoxi的優惠券每次限使用一張，無法合併使用，用戶可自行挑選最適合當前行程的優惠券折抵。</strong></p><p><strong><span style="color:#c0392b">限時領取 yoxi x Skyline Film 的專案優惠快上:</span></strong> <a href="https://yoxirider.page.link/V8rP">https://yoxirider.page.link/V8rP</a></p><hr><p><strong>活動場地</strong></p><p>稍稍遠離了信義區，我們來到內湖IKEA對面的台北建材中心。</p><p>走上8樓的露台空間，映入眼簾的是小半個台北盆地的景緻，還能遠眺101，印象中繁忙又擁擠的內湖區，在週五及週末的傍晚時光，反倒是多了幾分悠哉感。</p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403290745281483280010.jpg" style="height:450px; width:600px"></p><p style="text-align:center"><img src="https://static.accupass.com/eventintro/2403300952038797731210.jpg" style="height:800px; width:600px"></p><p><strong>場地: 台北設計建材中心</strong></p><p><strong>地點: 台北市內湖區新湖一路185號(內湖IKEA對面)</strong></p><p style="text-align:center"><strong><img alt="" src="https://static.accupass.com/eventintro/2403260810146550742380.jpg" style="height:442px; width:780px"></strong></p><hr><p>注意事項&nbsp;</p><ol><li>本活動必須年滿18歲以上才可參與，<strong><span style="color:#c0392b">配合電影分級制度，必要時</span></strong>請務必配合現場工作人員進行身份查證驗，請購票觀眾務必攜帶身分證件。</li><li><strong>購票入場即可免費獲得百威金尊330ml一罐!</strong></li><li><strong>活動現場提供BBQ燻肉販售，販售方式刷卡付現皆可!</strong></li><li><strong>台北設計建材中心附設付費停車場，屋頂電影院活動及現場食物酒水消費無法進行折抵。</strong></li><li><strong>活動座位為全躺椅，入座採先來先到制，我們不提供預先劃位或保留座位。</strong></li><li>每場電影結束後我們都會進行場地整理，若您有連續購買兩場電影的觀眾可留在原座位上無需重新入場，我們的工作人員會協助您進行check in。</li><li>請注意工作人員有權請觀眾調整座位以確保每位購票者都能入席。</li><li>如遇颱⾵或其他不可抗⼒之⾃然因素導致活動必須取消/延期，我們將會透過活動通Accupass購票頁面(本頁面)及Skyline Film Facebook官方粉絲團進⾏公告。若牽涉退票事宜，我們將會按照相關法令規定，並統⼀透過活動通Accupass辦理退費。</li><li>除不可抗⼒之⾃然因素外，票卷⼀旦售出，恕不退款，若無法參加活動，請將票券轉讓其他參加⼈。</li><li>活動現場請勿吸菸。</li><li>禁止酒後駕車。</li></ol>',
        '桃園市', '中壢區中大路300號', NOW() - INTERVAL 30 DAY,
        NOW() + INTERVAL 30 DAY, NOW() + INTERVAL 30 DAY - INTERVAL 10 HOUR , NOW() + INTERVAL 30 DAY, 1,
        5, 1, 0,100),
       ('Skyline Film 屋頂電影院', 7, 1,
        '<p><strong>台北好久不見!</strong></p><p><strong>這次4月在內湖的屋頂放映，連續兩週，十部電影，不變的經典無線耳機與木質躺椅，遠眺著小半個台北盆地，春日徐徐微風下，人手一杯啤酒與美式燻肉，暢快舒適的屋頂體驗，限定回歸!</strong></p><p><strong>4/19(Fri)-4/21(Sun)及4/26-4/28(Sun) 這兩週，我們刻意把週五的場次縮減為一場，將整體時間延後至8:30開演，下班下課的你們可以不用那麼趕了!</strong></p><p><strong>快把時間留下來，來屋頂找我們喝一杯看電影!</strong></p><p>&nbsp;</p><p><span style="color:#c0392b"><strong>凡購票入場觀眾即可免費獲得百威金尊330ml一罐!</strong></span></p><p><strong>期待天氣很好，我們4月內湖台北建材中心屋頂見!</strong></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403260546501777605381.jpg" style="height:400px; width:600px"></p><p style="text-align:center"><strong><img alt="" src="https://static.accupass.com/eventintro/2305190800441741697587.jpg" style="height:400px; width:600px"></strong></p><hr><p><strong>4/19(Fri)</strong><br><strong>20:30 Forrest Gump 阿甘正傳&nbsp;(19:30 開放入場 Open at 19:30)</strong><br><br><span style="color:#999999">阿甘坐在公園的長凳上，向往來的人說著自己的故事。</span></p><p><span style="color:#999999">他是美式足球明星、越戰英雄、乒乓球國家隊，現在更是個成功的生意人。</span></p><p><span style="color:#999999">但如果沒有母親和Jenny，他這輩子惦記最深的兩個人，阿甘或許不會有那麽不平凡的人生。</span></p><p><span style="color:#999999">而他的故事，要從那個腦袋不太靈光，肢體天生有些缺陷的孩子說起.....</span></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403250844331285794200.jpg" style="height:402px; width:600px"></p><p>&nbsp;</p><hr><p><strong>4/20(Sat) </strong></p><p><strong>18:30 In Bruges&nbsp;</strong><strong>殺手沒有假期</strong><strong> (17:30</strong><strong>開放入場</strong><strong> Open at 17:30)</strong></p><p><span style="color:#999999">Ken和Ray在完成了刺殺任務後，奉命來到比利時的布魯日度假。</span></p><p><span style="color:#999999">在充滿觀光客的中世紀小鎮裡，他倆參訪古蹟、看看藝術展覽與畫作，雖然挺輕鬆的，但怎麼樣都不像是兩個殺手喜歡待的地方。</span></p><p><span style="color:#999999">原來Ray在上次的刺殺任務中，雖然成功消滅目標，卻也誤殺了一位無辜的小男孩，而Ken也突然接到了上級的新任務，就是要殺了Ray......</span></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403250852257420067220.jpg" style="height:420px; width:600px"></p><p><strong>21:20 The Boat That Rocked&nbsp;海盜電台</strong><strong>&nbsp;(20:40</strong><strong>開放入場</strong><strong> Open at 20:40)</strong></p><p><span style="color:#999999">在英國60年代，人們唯一能夠聽到搖滾樂的地方，是一艘大西洋飄盪的破船 ── 一個在海上發送違法電波、百無禁忌的海盜電台。<br><br>但到了上了船之後，才知道這群改變世界的不法之徒，其實只是一群被貼上各種標籤的怪胎，用著生命愛著搖滾樂。<br><br>別忘了，搖滾樂只有一個規則，就是沒有規則。</span></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403250856291296833566.jpg" style="height:399px; width:600px"></p><hr><p><strong>4/21(Sun) </strong></p><p><strong>18:30 De Surpirse&nbsp;意外製造公司</strong><strong>&nbsp;(17:30 </strong><strong>開放入場</strong><strong> Open at 17:30)</strong></p><p><span style="color:#999999">歐洲西部的一座貴族莊園，雅各冷漠地看著母親死去，他心中早已對生命失去熱情，捨棄了一切，只想要找一個平靜而無痛的死法。</span></p><p><span style="color:#999999">一次偶然的機會，他發現了這間提供人生單程票的公司，在選購了不知道死法，前往極樂世界的「驚喜包」的旅程後，他卻遇到了唯一讓他動心的旅伴…</span></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403250902237384089740.jpg" style="height:337px; width:600px"></p><p><strong>21:15&nbsp;The Lunchbox&nbsp;美味情書</strong><strong>&nbsp;(20:35&nbsp;</strong><strong>開放入場</strong><strong> Open at 20:35)</strong></p><p><span style="color:#999999">百年來，孟買的火車便當快遞乎從不出錯。</span></p><p><span style="color:#999999">IIa今天特別開心，因為他親手為丈夫做的午餐便當吃得精光。她期望著透過親手製作的美味便當，挽回兩人日漸消散的感情。</span></p><p><span style="color:#999999">但與丈夫詢問後，IIa發現他的便當居然被送到了陌生人嘴裡。她接連送了幾次，發現便當從來沒有送到丈夫那兒，但每天卻都被吃的乾乾淨淨...</span></p><p><span style="color:#999999">IIa雖然對於送錯便當感到失望，但卻想對這個每天吃光她便當的陌生人說聲感謝，於是她留下了紙條在便當內。</span></p><p><span style="color:#999999">傍晚，IIa收到了來自Saajan的第一封回信......</span></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403260505041518195971.jpg" style="height:345px; width:600px"></p><hr><p><strong>4/26(Fri) </strong></p><p><strong>20:30 Nuovo Cinema Paradiso 新天堂樂園&nbsp;(19:30 </strong><strong>開放入場</strong><strong> Open at 19:30)</strong></p><p><span style="color:#999999">多年以後，當頑童Toto成為大導演Salvatore之後，他才回到一切開始的地方—早已荒廢的「天堂樂園電影院」。<br><br>曾經，「天堂樂園電影院」是世界上最棒的地方。因為那裡有電影、有笑聲，還有一個在放映室，守候著鎮民笑容的年老放映師Adelfio。Toto的電影夢在這裡萌芽，但也為了夢想，他必須離開家鄉...<br><br>記得每一次看電影的感動，而這一次，將會更深刻...</span></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403260511115616575040.jpg" style="height:312px; width:600px"></p><hr><p><strong>4/27(Sat)</strong></p><p><strong>18:30 Love Letter&nbsp;</strong><strong>情書</strong><strong> (17:30</strong><strong>開放入場</strong><strong> Open at 17:30) </strong></p><p><span style="color:#999999">渡邊博子的未婚夫藤井樹因山難過世。她的哀傷、憤怒、思念與愛，都像一封無法投遞的信一樣，壓得她無法呼吸。<br><br>半開玩笑地，她找出了藤井樹高中的畢業紀念冊，「你好嗎？我很好……」博子照著上頭的地址寫了信給他。然後，她收到了藤井樹的回信。早已拆遷的老家、早已死去的人，早在多年前結束的故事，因此重新轉動了起來……</span></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403260514019388401890.jpg" style="height:251px; width:600px"></p><p><strong>21:30 &nbsp;Seven Psychopaths</strong><strong> 瘋狗綁票令&nbsp;(20:50</strong><strong>開放入場</strong><strong> Open at 20:50) </strong></p><p><span style="color:#999999">Marty是個編劇，他想好了新劇本的標題 - Seven Psychopaths，正苦惱著內容該如何動筆。</span></p><p><span style="color:#999999">這天，無業的演員好友Billy來訪，談論到他與另一位老人Hans正在做的工作 - 職業狗狗綁匪，而好巧不巧，他們新的受害對象，正是黑道大哥Charlie最疼愛的小西施...</span></p><p><span style="color:#999999">而Marty也成為這場無厘頭綁票的受害者，四人一狗與他們周遭一切的相愛相殺，就此展開。</span></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403260517228821475190.jpg" style="height:400px; width:600px"></p><hr><p><strong>4/28(Sun)</strong></p><p><strong>18:30 The Worst Person in the World</strong><strong> 世界上最爛的人&nbsp;(17:30</strong><strong>開放入場</strong><strong> Open at 17:30) </strong></p><p><span style="color:#999999">年輕的你，應該適合矛盾與徬徨吧?&nbsp;</span></p><p><span style="color:#999999">Julie是個聰明、自信且充滿行動力的人，在學業、職涯與感情間，她總是投其所好，但也會果斷的踩下煞車。</span></p><p><span style="color:#999999">在看似打破傳統框架下的自在人生，Julie仍舊活得像是迷途鳥兒一般，拍打著翅膀，卻不知道目的地......</span></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403260521061977443713.jpg" style="height:401px; width:600px"></p><p>&nbsp;</p><p><strong>21:30 Annie Hall</strong><strong>&nbsp;安妮霍爾&nbsp;(20:50</strong><strong>開放入場</strong><strong> Open at 20:50) </strong></p><p><span style="color:#999999">Alvy Singer fall in love with Annie Hall。</span></p><p><span style="color:#999999">這就是全部的故事，就是全部的甜蜜、浪漫、悲傷與悔恨。</span></p><p><span style="color:#999999">簡單而複雜，因為Alvy是一個矛盾的人，他是一個憂傷不得志的喜劇演員，他需要愛卻又尖酸刻薄，他神經質又自以為是。是一個充滿缺點但無法讓人討厭的人，因為懦弱的他，如此勇敢的愛，畏縮的他，如此真誠的坦率。</span></p><p><span style="color:#999999">男人的脆弱，還有女人的美，以幽默掩飾安全感...Woody Allen是沒在客氣的!</span></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403260547311383970679.jpg" style="height:338px; width:600px"></p><hr><p><strong>Skyline 屋頂限定</strong></p><p>Skyline Film 與每一位合作夥伴在屋頂，帶給你們最特別的體驗。</p><p><strong><span style="color:#c0392b">由爅登煙醺在屋頂提供的各類不同菜單(燻肉產品無包含在票價中，需額外購買)</span></strong>，以原木慢火柴燒，軟嫩夠味的手撕豬與牛前胸，香嫩帶骨的豬肋排，配上夠味的墨西哥辣椒、酸黃瓜與生洋蔥末，最後淋上秘制香料BBQ醬，來屋頂就是可以這麼享受!</p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2302200818024123072500.jpg" style="height:400px; width:600px"></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2302200818501253558762.jpg" style="height:400px; width:600px"></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2305190701221976350000.jpg" style="height:446px; width:600px"></p><p>&nbsp;</p><p><strong>1.柴燒煙燻牛前胸肉+煙燻香腸或培根.搭配墨西哥辣椒.酸黃瓜.生洋蔥.特製B.B.Q.醬及餐包</strong> <span style="color:#c0392b"><strong>$350</strong></span></p><p><strong>2.柴燒煙燻手撕豬肉+煙燻香腸或培根搭配墨西哥辣椒. 酸黃瓜.生洋蔥.特製B.B.Q.醬及餐包<span style="color:#c0392b"> $280</span></strong></p><p><strong>3.柴燒煙燻雞腿+煙燻香腸或培根搭配墨西哥辣椒. 酸黃瓜.生洋蔥.特製B.B.Q.醬及餐包<span style="color:#c0392b"> $280</span></strong></p><p><strong>4.雙人分享餐[手撕豬]+[牛前胸]or[雞腿](2選1)&nbsp;+煙燻香腸或培根搭配墨西哥辣椒. 酸黃瓜.生洋蔥.特製B.B.Q.醬及餐包</strong> <span style="color:#c0392b"><strong>$600</strong></span></p><p><strong>5.柴燒煙燻分享派對燻肉(牛前胸.雞腿.手撕豬.適合3-4人)+煙燻香腸或培根搭配墨西哥辣椒. 酸黃瓜.生洋蔥.特製B.B.Q.醬及餐包</strong> <span style="color:#c0392b"><strong>$900</strong></span></p><p><strong>6. 煙燻手撕豬肉捲餅 <span style="color:#c0392b">$150 (僅提供第二場次)</span></strong></p><p><strong>7.煙燻雞腿捲餅 <span style="color:#c0392b">$150 (僅提供第二場次)</span></strong></p><p><strong>8. 煙燻牛前胸捲餅 <span style="color:#c0392b">$200 (僅提供第二場次)</span></strong></p><p><strong><span style="color:#c0392b">無論是一個人、兩個人，還是一群人，屋頂上的肉肉party，我們通通一網打盡!&nbsp;</span></strong></p><hr><p>購票即免費贈送最具王者風範的<strong>百威金尊一罐，單一品種麥芽啤酒的純正血統，純粹豐郁口感不只是絕妙的搭餐酒，百威金尊就像是皇冠上的珍珠，是屋頂電影院絕對不能少的心頭好。來屋頂用百威金尊，搭配精緻燻肉，悠閒的下班與週末時光，就該這樣過!</strong></p><p><strong>此外，百威金尊攤位每場次都將提供限量拍照打卡禮，快來小巧精美的金尊Stand bar找我們玩!</strong></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2302130555461514491948.jpg" style="height:338px; width:600px"></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2305190703379459226790.jpg" style="height:400px; width:600px"></p><hr><p><strong><span style="background-color:#f39c12">yoxi x&nbsp;Skyline Film&nbsp;屋頂電影院&nbsp;乘車專案</span></strong></p><p>內湖不方便? 電影結束怕沒有捷運? 想喝酒又怕回不了家?<br>yoxi攜手屋頂電影院推出多樣乘車專屬優惠，讓我們來解決你的交通煩惱!</p><p><strong><span style="color:#c0392b">1.&nbsp;&nbsp;新朋友限定：</span></strong> <span style="color:#c0392b">新用戶首次搭乘前輸入 [ skf2024 ］即可兌換$200優惠 （折抵面額為$50*4）</span>。新朋友優惠領取截止日為4/29 ，領取後60天內可搭乘使用。</p><p><strong><span style="color:#c0392b">2. 新朋友老朋友一起用：</span></strong> <span style="color:#c0392b">不分新舊用戶，只要於4/5-4/29活動宣傳期間，輸入[ 屋頂電影夜］預約叫車享2趟9折優惠，單趟最高折抵 $100</span>，領取後30天內可搭乘使用!</p><p><strong><span style="color:#c0392b">3. 放映期間限定:</span></strong> 不分新舊用戶，我們的工作人員將於每場次電影開演前公布現時神秘優惠碼，現場的觀眾們要好好把握機會啦!</p><p><strong>p.s.&nbsp;yoxi的優惠券每次限使用一張，無法合併使用，用戶可自行挑選最適合當前行程的優惠券折抵。</strong></p><p><strong><span style="color:#c0392b">限時領取 yoxi x Skyline Film 的專案優惠快上:</span></strong> <a href="https://yoxirider.page.link/V8rP">https://yoxirider.page.link/V8rP</a></p><hr><p><strong>活動場地</strong></p><p>稍稍遠離了信義區，我們來到內湖IKEA對面的台北建材中心。</p><p>走上8樓的露台空間，映入眼簾的是小半個台北盆地的景緻，還能遠眺101，印象中繁忙又擁擠的內湖區，在週五及週末的傍晚時光，反倒是多了幾分悠哉感。</p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2403290745281483280010.jpg" style="height:450px; width:600px"></p><p style="text-align:center"><img src="https://static.accupass.com/eventintro/2403300952038797731210.jpg" style="height:800px; width:600px"></p><p><strong>場地: 台北設計建材中心</strong></p><p><strong>地點: 台北市內湖區新湖一路185號(內湖IKEA對面)</strong></p><p style="text-align:center"><strong><img alt="" src="https://static.accupass.com/eventintro/2403260810146550742380.jpg" style="height:442px; width:780px"></strong></p><hr><p>注意事項&nbsp;</p><ol><li>本活動必須年滿18歲以上才可參與，<strong><span style="color:#c0392b">配合電影分級制度，必要時</span></strong>請務必配合現場工作人員進行身份查證驗，請購票觀眾務必攜帶身分證件。</li><li><strong>購票入場即可免費獲得百威金尊330ml一罐!</strong></li><li><strong>活動現場提供BBQ燻肉販售，販售方式刷卡付現皆可!</strong></li><li><strong>台北設計建材中心附設付費停車場，屋頂電影院活動及現場食物酒水消費無法進行折抵。</strong></li><li><strong>活動座位為全躺椅，入座採先來先到制，我們不提供預先劃位或保留座位。</strong></li><li>每場電影結束後我們都會進行場地整理，若您有連續購買兩場電影的觀眾可留在原座位上無需重新入場，我們的工作人員會協助您進行check in。</li><li>請注意工作人員有權請觀眾調整座位以確保每位購票者都能入席。</li><li>如遇颱⾵或其他不可抗⼒之⾃然因素導致活動必須取消/延期，我們將會透過活動通Accupass購票頁面(本頁面)及Skyline Film Facebook官方粉絲團進⾏公告。若牽涉退票事宜，我們將會按照相關法令規定，並統⼀透過活動通Accupass辦理退費。</li><li>除不可抗⼒之⾃然因素外，票卷⼀旦售出，恕不退款，若無法參加活動，請將票券轉讓其他參加⼈。</li><li>活動現場請勿吸菸。</li><li>禁止酒後駕車。</li></ol>',
        '桃園市', '中壢區中大路300號', NOW() - INTERVAL 20 DAY,
        NOW() + INTERVAL 20 DAY, NOW() + INTERVAL 20 DAY - INTERVAL 10 HOUR , NOW() + INTERVAL 20 DAY, 1,
        5, 1, 0,0),
       ('2024府城超級馬拉松', 1, 2, '<h3><strong>2024府城超級馬拉松<br />
<img alt="" data-src="https://imagedelivery.net/aJ0ay8hJuGoaVas3rlfLyg/activity/6050-0749052ccce270691f3ffab032387138/activity_content/images/20240411-140257-0411001.jpg/public" src="https://imagedelivery.net/aJ0ay8hJuGoaVas3rlfLyg/activity/6050-0749052ccce270691f3ffab032387138/activity_content/images/20240411-140257-0411001.jpg/public" style="height:311px; width:600px" /></strong></h3>

<p>活動宗旨</p>

<p>千馬精神憾動府城，2024府城超級馬拉松，伴你從黃昏到黎明，&nbsp;一圓你的超馬夢，護台億載，環繞金城，超馬老手的競賽與新手初試啼聲的殿堂，老將帶新手，讓超馬紮根未來。</p>

<p>主辦單位</p>

<p>百事樂運動行銷整合有限公司</p>

<p>承辦單位</p>

<p>府城國際超級馬拉松協會</p>

<p>協辦單位</p>

<p>陳亭妃立委服務處、李啟維議員服務處、林美燕議員服務處、盧崑福議員服務處、周麗津議員服務處、億載里鄭聰維里長服務處、豐赫電訊有限公司 、上星釣具行、神威創藝、府城國際超級馬拉松協會志工</p>

<p>贊助單位</p>

<p>陸續增加中</p>

<p>競賽日期</p>

<p>2024年09 月 21日（六）PM 17點30分起跑~2024年09 月 22日 (日) AM 08點00分結束</p>

<p>競賽路線</p>

<p>台南市億載金城外圍道路內圈1574公尺</p>

<p>各組別完成圈數如下:</p>

<p>11.02公里=1.574*<strong>7圈</strong></p>

<p>22.04公里=1.574*<strong>14圈</strong></p>

<p>42.5公里=1.574*<strong>27圈</strong></p>

<p>80.3公里=1.574*<strong>51圈</strong></p>

<p>100.7公里=1.574*<strong>64圈</strong></p>

<p><img alt="" data-src="https://imagedelivery.net/aJ0ay8hJuGoaVas3rlfLyg/activity/6050-0749052ccce270691f3ffab032387138/activity_content/images/20240411-140256-0411002.jpg/public" src="https://imagedelivery.net/aJ0ay8hJuGoaVas3rlfLyg/activity/6050-0749052ccce270691f3ffab032387138/activity_content/images/20240411-140256-0411002.jpg/public" style="height:633px; width:601px" /></p>

<p>競賽規則</p>

<p>&nbsp;</p>

<table>
	<tbody>
		<tr>
			<td><strong>組別</strong></td>
			<td><strong>起跑時間</strong></td>
		</tr>
		<tr>
			<td><strong>11.02公里組</strong></td>
			<td>09月21日17:30</td>
		</tr>
		<tr>
			<td><strong>11.02公里清晨組</strong></td>
			<td>09月22日05:30</td>
		</tr>
		<tr>
			<td><strong>22.04公里組</strong></td>
			<td>09月21日17:30</td>
		</tr>
		<tr>
			<td><strong>22.04公里深夜組</strong></td>
			<td>09月22日03:30</td>
		</tr>
		<tr>
			<td><strong>42.5公里組</strong></td>
			<td>09月21日17:30</td>
		</tr>
		<tr>
			<td><strong>42.5公里深夜組</strong></td>
			<td>09月22日00:45</td>
		</tr>
		<tr>
			<td><strong>80.3公里組</strong></td>
			<td>09月21日17:30</td>
		</tr>
		<tr>
			<td><strong>100.7公里組</strong></td>
			<td>09月21日17:30</td>
		</tr>
	</tbody>
</table>

<p>&nbsp;</p>

<p>活動流程</p>

<table>
	<tbody>
		<tr>
			<td><strong>時間</strong></td>
			<td><strong>活動項目</strong></td>
		</tr>
		<tr>
			<td>09/21(六) 16:30</td>
			<td>選手開始寄放衣物</td>
		</tr>
		<tr>
			<td>09/21(六) 17:30</td>
			<td><strong>所有一般組同時鳴槍起跑</strong></td>
		</tr>
		<tr>
			<td>09/21(六) 19:00</td>
			<td>11公里組結束</td>
		</tr>
		<tr>
			<td>09/21(六) 19:30</td>
			<td>11公里組頒獎</td>
		</tr>
		<tr>
			<td>09/21(六) 21:00</td>
			<td>22公里組結束</td>
		</tr>
		<tr>
			<td>09/21(六) 21:30</td>
			<td>22公里組頒獎</td>
		</tr>
		<tr>
			<td>09/22(日) 00:40</td>
			<td>42公里組結束</td>
		</tr>
		<tr>
			<td>09/22(日) 00:45</td>
			<td><strong>深夜組42公里鳴槍起跑</strong></td>
		</tr>
		<tr>
			<td>09/22(日) 01:10</td>
			<td>42公里組頒獎</td>
		</tr>
		<tr>
			<td>09/22(日) 03:30</td>
			<td><strong>深夜組22公里鳴槍起跑</strong></td>
		</tr>
		<tr>
			<td>09/22(日) 05:30</td>
			<td><strong>清晨組11公里鳴槍起跑</strong></td>
		</tr>
		<tr>
			<td>09/22(日) 07:00</td>
			<td>深夜組22公里清晨組11公里 80 公里結束</td>
		</tr>
		<tr>
			<td>09/22(日) 07:15</td>
			<td>清晨組11公里頒獎</td>
		</tr>
		<tr>
			<td>09/22(日) 07:25</td>
			<td>深夜組22公里頒獎</td>
		</tr>
		<tr>
			<td>09/22(日) 07:35</td>
			<td>80公里組頒獎</td>
		</tr>
		<tr>
			<td>09/22(日) 07:55</td>
			<td>深夜組42公里頒獎</td>
		</tr>
		<tr>
			<td>09/22(日) 08:00</td>
			<td>100公里組結束</td>
		</tr>
		<tr>
			<td>09/22(日) 08:15</td>
			<td>深夜組42公里頒獎</td>
		</tr>
		<tr>
			<td>09/22(日) 08:30</td>
			<td>100公里組頒獎</td>
		</tr>
		<tr>
			<td>09/22(日) 09:00</td>
			<td>2024府城超級馬拉松賽圓滿結束</td>
		</tr>
	</tbody>
</table>

<p>競賽項目、時間及報名費</p>

<table>
	<tbody>
		<tr>
			<td>&nbsp;</td>
			<td><strong>11.02</strong><strong>公里組</strong><br />
			<strong>（含清晨組）</strong></td>
			<td><strong>22.04</strong><strong>公里組</strong><br />
			<strong>(</strong><strong>含深夜組)</strong></td>
			<td><strong>42.5</strong><strong>公里組</strong><br />
			<strong>（含深夜組）</strong></td>
			<td><strong>80.3</strong>&nbsp;<strong>公里組</strong>&nbsp;<strong>&nbsp;</strong></td>
			<td><strong>100.7</strong>&nbsp;<strong>公里組</strong>&nbsp;<strong>&nbsp;</strong></td>
		</tr>
		<tr>
			<td><strong>報名費用</strong></td>
			<td><strong>750</strong></td>
			<td><strong>95</strong><strong>0</strong></td>
			<td><strong>1250</strong></td>
			<td><strong>2000</strong></td>
			<td><strong>2500</strong></td>
		</tr>
		<tr>
			<td><strong>起跑時間</strong></td>
			<td><strong>09/21 17:30</strong><br />
			<strong>（清晨組09/22 05:30）</strong></td>
			<td><strong>09/21 17:30</strong><br />
			<strong>（深夜組09/22 03:30）</strong></td>
			<td><strong>09/21 17:30</strong><br />
			<strong>（深夜組09/22&nbsp; 00:45）</strong></td>
			<td><strong>09/21 17:30</strong></td>
			<td><strong>09/21 17:30</strong></td>
		</tr>
		<tr>
			<td><strong>結束時間</strong></td>
			<td><strong>09/21 19:00</strong><br />
			<strong>（清晨組09/22&nbsp;</strong><strong>07</strong><strong>:00</strong><strong>）</strong></td>
			<td><strong>09/21 21:0</strong><strong>0</strong><br />
			<strong>（深夜組09/22 07:00）</strong></td>
			<td><strong>09/22 00:40</strong><br />
			<strong>（深夜組09/22&nbsp; 07:55）</strong></td>
			<td><strong>09/22 07:00</strong></td>
			<td><strong>09/22 08:00</strong></td>
		</tr>
		<tr>
			<td><strong>人數限制</strong></td>
			<td><strong>200<br />
			(</strong><strong>清晨組100)</strong></td>
			<td><strong>200<br />
			(</strong><strong>深夜組100)</strong></td>
			<td><strong>300<br />
			(</strong><strong>深夜組150)</strong></td>
			<td><strong>100</strong></td>
			<td><strong>200</strong></td>
		</tr>
	</tbody>
</table>

<p><strong>集合地點 : 億載金城大門口</strong></p>

<p>報名辦法</p>

<ul>
	<li>一律採網路報名，請上伊貝特報名系統。</li>
	<li>繳費方式：請於網路報名手續完成3天內，至超商列印繳費單繳交報名費。確認報名事項無誤，一經報名後即不接受更改；使用超商系統繳費者，建議於線上報名取得繳費序號後盡速於3天內完成繳費動作，如遇人數額滿，將無法進行繳費。</li>
	<li>報名手續完成者，<strong>在報名截止前</strong>，請至<a href="https://bao-ming.com/eb/service#contact" rel="noopener" target="_blank"><strong>伊貝特客服專區</strong></a>登記退費事宜，扣行政手續費100元，<strong>報名截止後一律不允許退費</strong>。</li>
	<li>網路報名如有任何問題請請至<a href="https://bao-ming.com/eb/service#contact" rel="noopener" target="_blank"><strong>伊貝特客服專區</strong></a>反饋或於上班時間致電02)2951-6969。</li>
	<li>報名登錄後需於3日內繳款始完成報名手續，未在期限內進行繳費者，視同放棄。</li>
	<li>繳費完成後，可至網站查詢確認繳費狀況，完成報名手續後不論任何理由所有報名資料均不得修改。</li>
	<li>重要選手基本資料例如：比賽項目、選手姓名、身分證字號及出生年月日等，請務必加強確認，倘資料錯誤造成當事人權益受損，概自行負責，不得異議。</li>
</ul>

<p>計時系統</p>

<p>本賽事的號碼布已內含晶片感應，請勿撕下，與號碼布一起配戴於衣服正面，以免感應不良。</p>

<p>衣物保管</p>

<p>大會於09/21（六）16:30起接受衣物保管，賽後憑號碼布領回。（貴重物品請自行保管，若有遺失，本會恕不負責。）</p>

<p>補給</p>

<ul>
	<li>本賽事為繞圈賽，大會設置1個大型補給站,另開放一個私補站</li>
	<li>大會補站除了提供基本補給.水、運動飲料、汽水、水果等等也會結合當地美食。</li>
</ul>

<p>相關資訊洽詢</p>

<ul>
	<li>有關網路報名任何問題：請至<a href="https://bao-ming.com/eb/service#contact" rel="noopener" target="_blank"><strong>伊貝特客服專區</strong></a>反饋或於上班時間致電02)2951-6969。</li>
	<li>有關競賽規程相關洽詢：0905-560-323 陳先生。</li>
	<li>賽後相關洽詢：&nbsp;<strong><a href="https://www.facebook.com/FuchengInternationSuperMarathonAssociation" rel="noopener" target="_blank">FB粉絲團{府城國際超級馬拉松協會}</a></strong>。</li>
</ul>

<p>物資領取</p>

<ul>
	<li>一律賽前郵遞報到，限臺灣境內；不接受郵政信箱。</li>
	<li>本賽是由「郵局包裹」方式寄送所有物資，預計於比賽前10日開始寄出，不提供現場報到，請務必填寫正確之可收地址，以免包裹無法送達；報名時應同時繳交運送代理費，費用如下：
	<table>
		<tbody>
			<tr>
				<td><strong>人數</strong></td>
				<td>　1~2人</td>
				<td>3~5人</td>
				<td>6~10人</td>
				<td>11~30人</td>
				<td>31-80人&nbsp;&nbsp;</td>
				<td>81人以上</td>
			</tr>
			<tr>
				<td><strong>費用</strong></td>
				<td>150元</td>
				<td>300元</td>
				<td>500元</td>
				<td>800元&nbsp;</td>
				<td>1000元</td>
				<td>1500元</td>
			</tr>
		</tbody>
	</table>
	</li>
	<li>比賽物品：紀念外套、號碼布(內含晶片感應)、衣保卡、別針等物品。</li>
	<li>未攜帶號碼布(內含晶片感應)者，將喪失比賽資格，不得進入比賽路線，裁判有權終止無號碼布及晶片選手進行比賽。</li>
</ul>

<p><strong>參賽禮</strong></p>

<table>
	<tbody>
		<tr>
			<td><strong>項目</strong></td>
			<td><strong>紀念外套</strong></td>
			<td><strong>號碼布</strong></td>
			<td><strong>保險</strong></td>
			<td><strong>計時晶片</strong></td>
		</tr>
		<tr>
			<td><strong>11公里組</strong></td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
		</tr>
		<tr>
			<td><strong>22公里組</strong></td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
		</tr>
		<tr>
			<td><strong>42公里組</strong></td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
		</tr>
		<tr>
			<td><strong>80公里組</strong></td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
		</tr>
		<tr>
			<td><strong>100公里組</strong></td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
		</tr>
	</tbody>
</table>

<p><strong>完賽禮</strong></p>

<table>
	<tbody>
		<tr>
			<td><strong>項目</strong></td>
			<td><strong>運動毛巾</strong></td>
			<td><strong>完賽獎牌</strong></td>
			<td><strong>成績證明</strong></td>
			<td><strong>餐點</strong></td>
			<td><strong>素食</strong></td>
			<td><strong>葷食</strong></td>
		</tr>
		<tr>
			<td><strong>11公里組</strong></td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
		</tr>
		<tr>
			<td><strong>22公里組</strong></td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
		</tr>
		<tr>
			<td><strong>42公里組</strong></td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
		</tr>
		<tr>
			<td><strong>80公里組</strong></td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
		</tr>
		<tr>
			<td><strong>100公里組</strong></td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
		</tr>
	</tbody>
</table>

<p>&nbsp;</p>

<p>停車場資訊</p>

<p>現有停車場或空地，總共可提供近1,600個汽車停車位，所有車位距離會場1.3公里以內,均可步行到會場。</p>

<ul>
	<li><strong>既有停車場、停車格</strong>

	<p>&nbsp;</p>

	<p><img alt="" data-src="https://imagedelivery.net/aJ0ay8hJuGoaVas3rlfLyg/activity/6050-0749052ccce270691f3ffab032387138/activity_content/images/20240411-153510-1712820855539.jpg/public" src="https://imagedelivery.net/aJ0ay8hJuGoaVas3rlfLyg/activity/6050-0749052ccce270691f3ffab032387138/activity_content/images/20240411-153510-1712820855539.jpg/public" style="height:294px; width:780px" /></p>
	</li>
	<li>開放路邊停車路段<br />
	<img alt="" data-src="https://imagedelivery.net/aJ0ay8hJuGoaVas3rlfLyg/activity/6050-0749052ccce270691f3ffab032387138/activity_content/images/20240411-153509-1712820890886.jpg/public" src="https://imagedelivery.net/aJ0ay8hJuGoaVas3rlfLyg/activity/6050-0749052ccce270691f3ffab032387138/activity_content/images/20240411-153509-1712820890886.jpg/public" style="height:281px; width:780px" /></li>
</ul>

<p>停車資訊</p>

<table>
	<tbody>
		<tr>
			<td><strong>停車場別</strong></td>
			<td><strong>小型車</strong></td>
			<td><strong>機車</strong></td>
		</tr>
		<tr>
			<td><strong>觀夕平台裡有大型停車場</strong></td>
			<td>100</td>
			<td>250</td>
		</tr>
	</tbody>
</table>

<p>分組方式</p>

<p>（一）11公里組：不分組別及性別，完賽發給證書與完賽獎牌。<br />
（二）22公里組、42公里組、80公里組、100公里組：依下列性別與年齡分組。</p>

<table>
	<tbody>
		<tr>
			<td><strong>組別</strong></td>
			<td><strong>男子組</strong></td>
			<td><strong>組別</strong></td>
			<td><strong>女子組</strong></td>
		</tr>
		<tr>
			<td><strong>男A組</strong></td>
			<td>72歲以上(41年次以前)</td>
			<td><strong>女A組</strong></td>
			<td>62歲以上(51年次以前)</td>
		</tr>
		<tr>
			<td><strong>男B組</strong></td>
			<td>62歲-71歲(民國51年-42年生)</td>
			<td><strong>女B組</strong></td>
			<td>47歲-61歲(民國66年-52年生)</td>
		</tr>
		<tr>
			<td><strong>男C組</strong></td>
			<td>52歲-61歲(民國61年-52年生)</td>
			<td><strong>女C組</strong></td>
			<td>32歲-46歲(民國81年-67年生)</td>
		</tr>
		<tr>
			<td><strong>男D組</strong></td>
			<td>42歲-51歲(民國71年-62年生)</td>
			<td><strong>女D組</strong></td>
			<td>20歲-31歲(民國93年-82年生)</td>
		</tr>
		<tr>
			<td><strong>男E組</strong></td>
			<td>32歲-41歲(民國81年-72年生)</td>
			<td><strong>女E組</strong></td>
			<td>19歲及以下(民國94年及以後出生)</td>
		</tr>
		<tr>
			<td><strong>男F組</strong></td>
			<td>20歲-31歲(民國93年-82年)</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><strong>男己組</strong></td>
			<td>19歲(民國94年及以後出生)</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
	</tbody>
</table>

<p>獎勵方式及辦法</p>

<ul>
	<li><strong>總名次</strong>

	<ul>
		<li><strong>100.7公里組：</strong>

		<table>
			<tbody>
				<tr>
					<td>&nbsp;</td>
					<td><strong>總名次男子組</strong></td>
					<td><strong>總名次女子組</strong></td>
				</tr>
				<tr>
					<td><strong>第一名</strong></td>
					<td>7000元+獎盃</td>
					<td>7000元+獎盃</td>
				</tr>
				<tr>
					<td><strong>第二名</strong></td>
					<td>6000元+獎盃</td>
					<td>6000元+獎盃</td>
				</tr>
				<tr>
					<td><strong>第三名</strong></td>
					<td>5000元+獎盃</td>
					<td>&nbsp;</td>
				</tr>
			</tbody>
		</table>
		</li>
		<li><strong>80.3公里組：</strong>
		<table>
			<tbody>
				<tr>
					<td>&nbsp;</td>
					<td><strong>總名次男子組</strong></td>
					<td><strong>總名次女子組</strong></td>
				</tr>
				<tr>
					<td><strong>第一名</strong></td>
					<td>5000元+獎盃</td>
					<td>5000元+獎盃</td>
				</tr>
				<tr>
					<td><strong>第二名</strong></td>
					<td>4000元+獎盃</td>
					<td>&nbsp;</td>
				</tr>
			</tbody>
		</table>
		</li>
		<li><strong>42.5公里組：</strong>
		<table>
			<tbody>
				<tr>
					<td>&nbsp;</td>
					<td><strong>總名次男子組</strong></td>
					<td><strong>總名次女子組</strong></td>
				</tr>
				<tr>
					<td><strong>第一名</strong></td>
					<td>4000元+獎盃</td>
					<td>4000元+獎盃</td>
				</tr>
				<tr>
					<td><strong>第二名</strong></td>
					<td>3000元+獎盃</td>
					<td>3000元+獎盃</td>
				</tr>
				<tr>
					<td><strong>第三名</strong></td>
					<td>2000元+獎盃</td>
					<td>&nbsp;</td>
				</tr>
			</tbody>
		</table>
		</li>
		<li><strong>42.5公里（深夜組）</strong><br />
		依報名人數：　 男60名頒發總名次第1名獎金 4000元+獎盃 男90名頒發總名次第2名獎金 3000元+獎盃 男120名頒發總名次第3名獎金 2000元+獎盃&nbsp;<strong>未達報名人數只頒發男總名次前3名獎盃.並取消各分組獎項。</strong>&nbsp;依報名人數：　 女30名頒發總名次第1名獎金 4000元+獎盃 女50名頒發總名次第2名獎金 3000元+獎盃 女60名頒發總名次第3名獎金 2000元+獎盃&nbsp;<strong>未達報名人數只頒發男總名次前3名獎盃.並取消各分組獎項。</strong></li>
		<li>&nbsp;<strong><strong>22.04公里組：</strong></strong>
		<table>
			<tbody>
				<tr>
					<td>&nbsp;</td>
					<td><strong>總名次男子組</strong></td>
					<td><strong>總名次女子組</strong></td>
				</tr>
				<tr>
					<td><strong>第一名</strong></td>
					<td>2000元+獎盃</td>
					<td>2000元+獎盃</td>
				</tr>
				<tr>
					<td><strong>第二名</strong></td>
					<td>1500元+獎盃</td>
					<td>1500元+獎盃</td>
				</tr>
				<tr>
					<td><strong>第三名</strong></td>
					<td>1000元+獎盃</td>
					<td>1000元+獎盃</td>
				</tr>
			</tbody>
		</table>
		</li>
		<li><strong><strong>22.04公里（深夜組）</strong></strong><br />
		依報名人數：　 男40名頒發總名次第1名獎金 2000元+獎盃 男65名頒發總名次第2名獎金 1500元+獎盃 男80名頒發總名次第3名獎金 1000元+獎盃&nbsp;<strong>未達報名人數只頒發男總名次前3名獎盃.並取消各分組獎項。</strong>&nbsp;依報名人數：　 女20名頒發總名次第1名獎金 2000元+獎盃 女40名頒發總名次第2名獎金 1500元+獎盃 女50名頒發總名次第3名獎金 1000元+獎盃&nbsp;<strong>未達報名人數只頒發男總名次前3名獎盃.並取消各分組獎項。</strong></li>
		<li><strong>11公里組：</strong><br />
		&nbsp;不設獎金，總名次男子組、女子組前6名，各贈獎盃一只。不另設分組。</li>
		<li><strong><strong>11公里（清晨組）：</strong></strong>
		<p>不設獎金，男子組取總名次前3名，女生組總名次3名，獎盃各一座，不另設分組。</p>
		</li>
	</ul>
	</li>
	<li><strong>分組名次</strong><br />
	22公里組、42公里組、80公里、100公里：各組依參賽人數比例，除完賽獎牌外，分組頒發獎盃，比例如下：
	<table>
		<tbody>
			<tr>
				<td><strong>各組人數</strong></td>
				<td><strong>取獎人數</strong></td>
			</tr>
			<tr>
				<td><strong>50人以下</strong></td>
				<td>3名</td>
			</tr>
			<tr>
				<td><strong>51~100人</strong></td>
				<td>6名</td>
			</tr>
			<tr>
				<td><strong>101~200人</strong></td>
				<td>10名</td>
			</tr>
			<tr>
				<td><strong>201人以上</strong></td>
				<td>15名</td>
			</tr>
		</tbody>
	</table>
	</li>
	<li><strong>注意事項</strong>
	<ul>
		<li>得獎選手有義務提供身分證件，以茲證明身份避免選手間爭議。</li>
		<li>總名次得獎選手，不再列入分組名次獎勵。</li>
	</ul>
	</li>
</ul>
', '台北市', '信義區松壽路1號', '2024-4-2 00:00:00', '2024-9-22 08:00:00',
        '2024-9-21 17:30:00', '2024-9-22 08:00:00', 1, 5, 1, 0,60),
       ('2024府城超級馬拉松', 1, 2, '<h3><strong>2024府城超級馬拉松<br />
<img alt="" data-src="https://imagedelivery.net/aJ0ay8hJuGoaVas3rlfLyg/activity/6050-0749052ccce270691f3ffab032387138/activity_content/images/20240411-140257-0411001.jpg/public" src="https://imagedelivery.net/aJ0ay8hJuGoaVas3rlfLyg/activity/6050-0749052ccce270691f3ffab032387138/activity_content/images/20240411-140257-0411001.jpg/public" style="height:311px; width:600px" /></strong></h3>

<p>活動宗旨</p>

<p>千馬精神憾動府城，2024府城超級馬拉松，伴你從黃昏到黎明，&nbsp;一圓你的超馬夢，護台億載，環繞金城，超馬老手的競賽與新手初試啼聲的殿堂，老將帶新手，讓超馬紮根未來。</p>

<p>主辦單位</p>

<p>百事樂運動行銷整合有限公司</p>

<p>承辦單位</p>

<p>府城國際超級馬拉松協會</p>

<p>協辦單位</p>

<p>陳亭妃立委服務處、李啟維議員服務處、林美燕議員服務處、盧崑福議員服務處、周麗津議員服務處、億載里鄭聰維里長服務處、豐赫電訊有限公司 、上星釣具行、神威創藝、府城國際超級馬拉松協會志工</p>

<p>贊助單位</p>

<p>陸續增加中</p>

<p>競賽日期</p>

<p>2024年09 月 21日（六）PM 17點30分起跑~2024年09 月 22日 (日) AM 08點00分結束</p>

<p>競賽路線</p>

<p>台南市億載金城外圍道路內圈1574公尺</p>

<p>各組別完成圈數如下:</p>

<p>11.02公里=1.574*<strong>7圈</strong></p>

<p>22.04公里=1.574*<strong>14圈</strong></p>

<p>42.5公里=1.574*<strong>27圈</strong></p>

<p>80.3公里=1.574*<strong>51圈</strong></p>

<p>100.7公里=1.574*<strong>64圈</strong></p>

<p><img alt="" data-src="https://imagedelivery.net/aJ0ay8hJuGoaVas3rlfLyg/activity/6050-0749052ccce270691f3ffab032387138/activity_content/images/20240411-140256-0411002.jpg/public" src="https://imagedelivery.net/aJ0ay8hJuGoaVas3rlfLyg/activity/6050-0749052ccce270691f3ffab032387138/activity_content/images/20240411-140256-0411002.jpg/public" style="height:633px; width:601px" /></p>

<p>競賽規則</p>

<p>&nbsp;</p>

<table>
	<tbody>
		<tr>
			<td><strong>組別</strong></td>
			<td><strong>起跑時間</strong></td>
		</tr>
		<tr>
			<td><strong>11.02公里組</strong></td>
			<td>09月21日17:30</td>
		</tr>
		<tr>
			<td><strong>11.02公里清晨組</strong></td>
			<td>09月22日05:30</td>
		</tr>
		<tr>
			<td><strong>22.04公里組</strong></td>
			<td>09月21日17:30</td>
		</tr>
		<tr>
			<td><strong>22.04公里深夜組</strong></td>
			<td>09月22日03:30</td>
		</tr>
		<tr>
			<td><strong>42.5公里組</strong></td>
			<td>09月21日17:30</td>
		</tr>
		<tr>
			<td><strong>42.5公里深夜組</strong></td>
			<td>09月22日00:45</td>
		</tr>
		<tr>
			<td><strong>80.3公里組</strong></td>
			<td>09月21日17:30</td>
		</tr>
		<tr>
			<td><strong>100.7公里組</strong></td>
			<td>09月21日17:30</td>
		</tr>
	</tbody>
</table>

<p>&nbsp;</p>

<p>活動流程</p>

<table>
	<tbody>
		<tr>
			<td><strong>時間</strong></td>
			<td><strong>活動項目</strong></td>
		</tr>
		<tr>
			<td>09/21(六) 16:30</td>
			<td>選手開始寄放衣物</td>
		</tr>
		<tr>
			<td>09/21(六) 17:30</td>
			<td><strong>所有一般組同時鳴槍起跑</strong></td>
		</tr>
		<tr>
			<td>09/21(六) 19:00</td>
			<td>11公里組結束</td>
		</tr>
		<tr>
			<td>09/21(六) 19:30</td>
			<td>11公里組頒獎</td>
		</tr>
		<tr>
			<td>09/21(六) 21:00</td>
			<td>22公里組結束</td>
		</tr>
		<tr>
			<td>09/21(六) 21:30</td>
			<td>22公里組頒獎</td>
		</tr>
		<tr>
			<td>09/22(日) 00:40</td>
			<td>42公里組結束</td>
		</tr>
		<tr>
			<td>09/22(日) 00:45</td>
			<td><strong>深夜組42公里鳴槍起跑</strong></td>
		</tr>
		<tr>
			<td>09/22(日) 01:10</td>
			<td>42公里組頒獎</td>
		</tr>
		<tr>
			<td>09/22(日) 03:30</td>
			<td><strong>深夜組22公里鳴槍起跑</strong></td>
		</tr>
		<tr>
			<td>09/22(日) 05:30</td>
			<td><strong>清晨組11公里鳴槍起跑</strong></td>
		</tr>
		<tr>
			<td>09/22(日) 07:00</td>
			<td>深夜組22公里清晨組11公里 80 公里結束</td>
		</tr>
		<tr>
			<td>09/22(日) 07:15</td>
			<td>清晨組11公里頒獎</td>
		</tr>
		<tr>
			<td>09/22(日) 07:25</td>
			<td>深夜組22公里頒獎</td>
		</tr>
		<tr>
			<td>09/22(日) 07:35</td>
			<td>80公里組頒獎</td>
		</tr>
		<tr>
			<td>09/22(日) 07:55</td>
			<td>深夜組42公里頒獎</td>
		</tr>
		<tr>
			<td>09/22(日) 08:00</td>
			<td>100公里組結束</td>
		</tr>
		<tr>
			<td>09/22(日) 08:15</td>
			<td>深夜組42公里頒獎</td>
		</tr>
		<tr>
			<td>09/22(日) 08:30</td>
			<td>100公里組頒獎</td>
		</tr>
		<tr>
			<td>09/22(日) 09:00</td>
			<td>2024府城超級馬拉松賽圓滿結束</td>
		</tr>
	</tbody>
</table>

<p>競賽項目、時間及報名費</p>

<table>
	<tbody>
		<tr>
			<td>&nbsp;</td>
			<td><strong>11.02</strong><strong>公里組</strong><br />
			<strong>（含清晨組）</strong></td>
			<td><strong>22.04</strong><strong>公里組</strong><br />
			<strong>(</strong><strong>含深夜組)</strong></td>
			<td><strong>42.5</strong><strong>公里組</strong><br />
			<strong>（含深夜組）</strong></td>
			<td><strong>80.3</strong>&nbsp;<strong>公里組</strong>&nbsp;<strong>&nbsp;</strong></td>
			<td><strong>100.7</strong>&nbsp;<strong>公里組</strong>&nbsp;<strong>&nbsp;</strong></td>
		</tr>
		<tr>
			<td><strong>報名費用</strong></td>
			<td><strong>750</strong></td>
			<td><strong>95</strong><strong>0</strong></td>
			<td><strong>1250</strong></td>
			<td><strong>2000</strong></td>
			<td><strong>2500</strong></td>
		</tr>
		<tr>
			<td><strong>起跑時間</strong></td>
			<td><strong>09/21 17:30</strong><br />
			<strong>（清晨組09/22 05:30）</strong></td>
			<td><strong>09/21 17:30</strong><br />
			<strong>（深夜組09/22 03:30）</strong></td>
			<td><strong>09/21 17:30</strong><br />
			<strong>（深夜組09/22&nbsp; 00:45）</strong></td>
			<td><strong>09/21 17:30</strong></td>
			<td><strong>09/21 17:30</strong></td>
		</tr>
		<tr>
			<td><strong>結束時間</strong></td>
			<td><strong>09/21 19:00</strong><br />
			<strong>（清晨組09/22&nbsp;</strong><strong>07</strong><strong>:00</strong><strong>）</strong></td>
			<td><strong>09/21 21:0</strong><strong>0</strong><br />
			<strong>（深夜組09/22 07:00）</strong></td>
			<td><strong>09/22 00:40</strong><br />
			<strong>（深夜組09/22&nbsp; 07:55）</strong></td>
			<td><strong>09/22 07:00</strong></td>
			<td><strong>09/22 08:00</strong></td>
		</tr>
		<tr>
			<td><strong>人數限制</strong></td>
			<td><strong>200<br />
			(</strong><strong>清晨組100)</strong></td>
			<td><strong>200<br />
			(</strong><strong>深夜組100)</strong></td>
			<td><strong>300<br />
			(</strong><strong>深夜組150)</strong></td>
			<td><strong>100</strong></td>
			<td><strong>200</strong></td>
		</tr>
	</tbody>
</table>

<p><strong>集合地點 : 億載金城大門口</strong></p>

<p>報名辦法</p>

<ul>
	<li>一律採網路報名，請上伊貝特報名系統。</li>
	<li>繳費方式：請於網路報名手續完成3天內，至超商列印繳費單繳交報名費。確認報名事項無誤，一經報名後即不接受更改；使用超商系統繳費者，建議於線上報名取得繳費序號後盡速於3天內完成繳費動作，如遇人數額滿，將無法進行繳費。</li>
	<li>報名手續完成者，<strong>在報名截止前</strong>，請至<a href="https://bao-ming.com/eb/service#contact" rel="noopener" target="_blank"><strong>伊貝特客服專區</strong></a>登記退費事宜，扣行政手續費100元，<strong>報名截止後一律不允許退費</strong>。</li>
	<li>網路報名如有任何問題請請至<a href="https://bao-ming.com/eb/service#contact" rel="noopener" target="_blank"><strong>伊貝特客服專區</strong></a>反饋或於上班時間致電02)2951-6969。</li>
	<li>報名登錄後需於3日內繳款始完成報名手續，未在期限內進行繳費者，視同放棄。</li>
	<li>繳費完成後，可至網站查詢確認繳費狀況，完成報名手續後不論任何理由所有報名資料均不得修改。</li>
	<li>重要選手基本資料例如：比賽項目、選手姓名、身分證字號及出生年月日等，請務必加強確認，倘資料錯誤造成當事人權益受損，概自行負責，不得異議。</li>
</ul>

<p>計時系統</p>

<p>本賽事的號碼布已內含晶片感應，請勿撕下，與號碼布一起配戴於衣服正面，以免感應不良。</p>

<p>衣物保管</p>

<p>大會於09/21（六）16:30起接受衣物保管，賽後憑號碼布領回。（貴重物品請自行保管，若有遺失，本會恕不負責。）</p>

<p>補給</p>

<ul>
	<li>本賽事為繞圈賽，大會設置1個大型補給站,另開放一個私補站</li>
	<li>大會補站除了提供基本補給.水、運動飲料、汽水、水果等等也會結合當地美食。</li>
</ul>

<p>相關資訊洽詢</p>

<ul>
	<li>有關網路報名任何問題：請至<a href="https://bao-ming.com/eb/service#contact" rel="noopener" target="_blank"><strong>伊貝特客服專區</strong></a>反饋或於上班時間致電02)2951-6969。</li>
	<li>有關競賽規程相關洽詢：0905-560-323 陳先生。</li>
	<li>賽後相關洽詢：&nbsp;<strong><a href="https://www.facebook.com/FuchengInternationSuperMarathonAssociation" rel="noopener" target="_blank">FB粉絲團{府城國際超級馬拉松協會}</a></strong>。</li>
</ul>

<p>物資領取</p>

<ul>
	<li>一律賽前郵遞報到，限臺灣境內；不接受郵政信箱。</li>
	<li>本賽是由「郵局包裹」方式寄送所有物資，預計於比賽前10日開始寄出，不提供現場報到，請務必填寫正確之可收地址，以免包裹無法送達；報名時應同時繳交運送代理費，費用如下：
	<table>
		<tbody>
			<tr>
				<td><strong>人數</strong></td>
				<td>　1~2人</td>
				<td>3~5人</td>
				<td>6~10人</td>
				<td>11~30人</td>
				<td>31-80人&nbsp;&nbsp;</td>
				<td>81人以上</td>
			</tr>
			<tr>
				<td><strong>費用</strong></td>
				<td>150元</td>
				<td>300元</td>
				<td>500元</td>
				<td>800元&nbsp;</td>
				<td>1000元</td>
				<td>1500元</td>
			</tr>
		</tbody>
	</table>
	</li>
	<li>比賽物品：紀念外套、號碼布(內含晶片感應)、衣保卡、別針等物品。</li>
	<li>未攜帶號碼布(內含晶片感應)者，將喪失比賽資格，不得進入比賽路線，裁判有權終止無號碼布及晶片選手進行比賽。</li>
</ul>

<p><strong>參賽禮</strong></p>

<table>
	<tbody>
		<tr>
			<td><strong>項目</strong></td>
			<td><strong>紀念外套</strong></td>
			<td><strong>號碼布</strong></td>
			<td><strong>保險</strong></td>
			<td><strong>計時晶片</strong></td>
		</tr>
		<tr>
			<td><strong>11公里組</strong></td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
		</tr>
		<tr>
			<td><strong>22公里組</strong></td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
		</tr>
		<tr>
			<td><strong>42公里組</strong></td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
		</tr>
		<tr>
			<td><strong>80公里組</strong></td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
		</tr>
		<tr>
			<td><strong>100公里組</strong></td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
		</tr>
	</tbody>
</table>

<p><strong>完賽禮</strong></p>

<table>
	<tbody>
		<tr>
			<td><strong>項目</strong></td>
			<td><strong>運動毛巾</strong></td>
			<td><strong>完賽獎牌</strong></td>
			<td><strong>成績證明</strong></td>
			<td><strong>餐點</strong></td>
			<td><strong>素食</strong></td>
			<td><strong>葷食</strong></td>
		</tr>
		<tr>
			<td><strong>11公里組</strong></td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
		</tr>
		<tr>
			<td><strong>22公里組</strong></td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
		</tr>
		<tr>
			<td><strong>42公里組</strong></td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
		</tr>
		<tr>
			<td><strong>80公里組</strong></td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
		</tr>
		<tr>
			<td><strong>100公里組</strong></td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
			<td>O</td>
		</tr>
	</tbody>
</table>

<p>&nbsp;</p>

<p>停車場資訊</p>

<p>現有停車場或空地，總共可提供近1,600個汽車停車位，所有車位距離會場1.3公里以內,均可步行到會場。</p>

<ul>
	<li><strong>既有停車場、停車格</strong>

	<p>&nbsp;</p>

	<p><img alt="" data-src="https://imagedelivery.net/aJ0ay8hJuGoaVas3rlfLyg/activity/6050-0749052ccce270691f3ffab032387138/activity_content/images/20240411-153510-1712820855539.jpg/public" src="https://imagedelivery.net/aJ0ay8hJuGoaVas3rlfLyg/activity/6050-0749052ccce270691f3ffab032387138/activity_content/images/20240411-153510-1712820855539.jpg/public" style="height:294px; width:780px" /></p>
	</li>
	<li>開放路邊停車路段<br />
	<img alt="" data-src="https://imagedelivery.net/aJ0ay8hJuGoaVas3rlfLyg/activity/6050-0749052ccce270691f3ffab032387138/activity_content/images/20240411-153509-1712820890886.jpg/public" src="https://imagedelivery.net/aJ0ay8hJuGoaVas3rlfLyg/activity/6050-0749052ccce270691f3ffab032387138/activity_content/images/20240411-153509-1712820890886.jpg/public" style="height:281px; width:780px" /></li>
</ul>

<p>停車資訊</p>

<table>
	<tbody>
		<tr>
			<td><strong>停車場別</strong></td>
			<td><strong>小型車</strong></td>
			<td><strong>機車</strong></td>
		</tr>
		<tr>
			<td><strong>觀夕平台裡有大型停車場</strong></td>
			<td>100</td>
			<td>250</td>
		</tr>
	</tbody>
</table>

<p>分組方式</p>

<p>（一）11公里組：不分組別及性別，完賽發給證書與完賽獎牌。<br />
（二）22公里組、42公里組、80公里組、100公里組：依下列性別與年齡分組。</p>

<table>
	<tbody>
		<tr>
			<td><strong>組別</strong></td>
			<td><strong>男子組</strong></td>
			<td><strong>組別</strong></td>
			<td><strong>女子組</strong></td>
		</tr>
		<tr>
			<td><strong>男A組</strong></td>
			<td>72歲以上(41年次以前)</td>
			<td><strong>女A組</strong></td>
			<td>62歲以上(51年次以前)</td>
		</tr>
		<tr>
			<td><strong>男B組</strong></td>
			<td>62歲-71歲(民國51年-42年生)</td>
			<td><strong>女B組</strong></td>
			<td>47歲-61歲(民國66年-52年生)</td>
		</tr>
		<tr>
			<td><strong>男C組</strong></td>
			<td>52歲-61歲(民國61年-52年生)</td>
			<td><strong>女C組</strong></td>
			<td>32歲-46歲(民國81年-67年生)</td>
		</tr>
		<tr>
			<td><strong>男D組</strong></td>
			<td>42歲-51歲(民國71年-62年生)</td>
			<td><strong>女D組</strong></td>
			<td>20歲-31歲(民國93年-82年生)</td>
		</tr>
		<tr>
			<td><strong>男E組</strong></td>
			<td>32歲-41歲(民國81年-72年生)</td>
			<td><strong>女E組</strong></td>
			<td>19歲及以下(民國94年及以後出生)</td>
		</tr>
		<tr>
			<td><strong>男F組</strong></td>
			<td>20歲-31歲(民國93年-82年)</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><strong>男己組</strong></td>
			<td>19歲(民國94年及以後出生)</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
	</tbody>
</table>

<p>獎勵方式及辦法</p>

<ul>
	<li><strong>總名次</strong>

	<ul>
		<li><strong>100.7公里組：</strong>

		<table>
			<tbody>
				<tr>
					<td>&nbsp;</td>
					<td><strong>總名次男子組</strong></td>
					<td><strong>總名次女子組</strong></td>
				</tr>
				<tr>
					<td><strong>第一名</strong></td>
					<td>7000元+獎盃</td>
					<td>7000元+獎盃</td>
				</tr>
				<tr>
					<td><strong>第二名</strong></td>
					<td>6000元+獎盃</td>
					<td>6000元+獎盃</td>
				</tr>
				<tr>
					<td><strong>第三名</strong></td>
					<td>5000元+獎盃</td>
					<td>&nbsp;</td>
				</tr>
			</tbody>
		</table>
		</li>
		<li><strong>80.3公里組：</strong>
		<table>
			<tbody>
				<tr>
					<td>&nbsp;</td>
					<td><strong>總名次男子組</strong></td>
					<td><strong>總名次女子組</strong></td>
				</tr>
				<tr>
					<td><strong>第一名</strong></td>
					<td>5000元+獎盃</td>
					<td>5000元+獎盃</td>
				</tr>
				<tr>
					<td><strong>第二名</strong></td>
					<td>4000元+獎盃</td>
					<td>&nbsp;</td>
				</tr>
			</tbody>
		</table>
		</li>
		<li><strong>42.5公里組：</strong>
		<table>
			<tbody>
				<tr>
					<td>&nbsp;</td>
					<td><strong>總名次男子組</strong></td>
					<td><strong>總名次女子組</strong></td>
				</tr>
				<tr>
					<td><strong>第一名</strong></td>
					<td>4000元+獎盃</td>
					<td>4000元+獎盃</td>
				</tr>
				<tr>
					<td><strong>第二名</strong></td>
					<td>3000元+獎盃</td>
					<td>3000元+獎盃</td>
				</tr>
				<tr>
					<td><strong>第三名</strong></td>
					<td>2000元+獎盃</td>
					<td>&nbsp;</td>
				</tr>
			</tbody>
		</table>
		</li>
		<li><strong>42.5公里（深夜組）</strong><br />
		依報名人數：　 男60名頒發總名次第1名獎金 4000元+獎盃 男90名頒發總名次第2名獎金 3000元+獎盃 男120名頒發總名次第3名獎金 2000元+獎盃&nbsp;<strong>未達報名人數只頒發男總名次前3名獎盃.並取消各分組獎項。</strong>&nbsp;依報名人數：　 女30名頒發總名次第1名獎金 4000元+獎盃 女50名頒發總名次第2名獎金 3000元+獎盃 女60名頒發總名次第3名獎金 2000元+獎盃&nbsp;<strong>未達報名人數只頒發男總名次前3名獎盃.並取消各分組獎項。</strong></li>
		<li>&nbsp;<strong><strong>22.04公里組：</strong></strong>
		<table>
			<tbody>
				<tr>
					<td>&nbsp;</td>
					<td><strong>總名次男子組</strong></td>
					<td><strong>總名次女子組</strong></td>
				</tr>
				<tr>
					<td><strong>第一名</strong></td>
					<td>2000元+獎盃</td>
					<td>2000元+獎盃</td>
				</tr>
				<tr>
					<td><strong>第二名</strong></td>
					<td>1500元+獎盃</td>
					<td>1500元+獎盃</td>
				</tr>
				<tr>
					<td><strong>第三名</strong></td>
					<td>1000元+獎盃</td>
					<td>1000元+獎盃</td>
				</tr>
			</tbody>
		</table>
		</li>
		<li><strong><strong>22.04公里（深夜組）</strong></strong><br />
		依報名人數：　 男40名頒發總名次第1名獎金 2000元+獎盃 男65名頒發總名次第2名獎金 1500元+獎盃 男80名頒發總名次第3名獎金 1000元+獎盃&nbsp;<strong>未達報名人數只頒發男總名次前3名獎盃.並取消各分組獎項。</strong>&nbsp;依報名人數：　 女20名頒發總名次第1名獎金 2000元+獎盃 女40名頒發總名次第2名獎金 1500元+獎盃 女50名頒發總名次第3名獎金 1000元+獎盃&nbsp;<strong>未達報名人數只頒發男總名次前3名獎盃.並取消各分組獎項。</strong></li>
		<li><strong>11公里組：</strong><br />
		&nbsp;不設獎金，總名次男子組、女子組前6名，各贈獎盃一只。不另設分組。</li>
		<li><strong><strong>11公里（清晨組）：</strong></strong>
		<p>不設獎金，男子組取總名次前3名，女生組總名次3名，獎盃各一座，不另設分組。</p>
		</li>
	</ul>
	</li>
	<li><strong>分組名次</strong><br />
	22公里組、42公里組、80公里、100公里：各組依參賽人數比例，除完賽獎牌外，分組頒發獎盃，比例如下：
	<table>
		<tbody>
			<tr>
				<td><strong>各組人數</strong></td>
				<td><strong>取獎人數</strong></td>
			</tr>
			<tr>
				<td><strong>50人以下</strong></td>
				<td>3名</td>
			</tr>
			<tr>
				<td><strong>51~100人</strong></td>
				<td>6名</td>
			</tr>
			<tr>
				<td><strong>101~200人</strong></td>
				<td>10名</td>
			</tr>
			<tr>
				<td><strong>201人以上</strong></td>
				<td>15名</td>
			</tr>
		</tbody>
	</table>
	</li>
	<li><strong>注意事項</strong>
	<ul>
		<li>得獎選手有義務提供身分證件，以茲證明身份避免選手間爭議。</li>
		<li>總名次得獎選手，不再列入分組名次獎勵。</li>
	</ul>
	</li>
</ul>
', '台北市', '信義區松壽路1號', NOW() - INTERVAL 20 DAY,
        NOW() + INTERVAL 40 DAY, NOW() + INTERVAL 40 DAY - INTERVAL 10 HOUR , NOW() + INTERVAL 40 DAY, 1, 5, 1, 0,0),
       ('2024清華校慶校友活動—清紫野餐派對x草地音樂會', 2, 3,
        '<p><span style="font-size:1.5em"><strong><u><span style="color:#cc0000">3/15(五)下午1:00起</span></u>開放報名，截止至票券全數售完為止。</strong></span></p><p><img alt="圖片" src="https://alumni.site.nthu.edu.tw/var/file/346/1346/img/817647729.png" style="height:400px; width:800px"></p><h2 style="text-align:center"><span style="font-size:1.625em"><strong>╭。☆║2024清紫野餐派對 ✘ 草地音樂會來啦║☆。╮ </strong></span></h2><p style="text-align:center"><span style="font-size:1.25em"><strong>為慶祝國立清華大學創校 113 週年暨在臺建校 68 週年生日快樂</strong></span></p><p style="text-align:center"><span style="font-size:1.25em"><strong>校友中心、校友總會邀請各地清華人，一起回母校同樂</strong></span></p><p style="text-align:center"><span style="font-size:1.25em"><strong>活動當天將有high翻全場的歌手表演、學生社團表演，及豐富多元的市集、動手DIY</strong></span></p><p style="text-align:center"><span style="font-size:1.25em"><strong>最重要的是，絕對不能錯過精彩的抽獎摸彩活動🎁</strong></span></p><hr><p style="text-align:justify"><span style="font-size:1.25em"><strong>▌日期</strong></span></p><p><span style="font-size:1.5em"><strong><span style="color:#cc0000">2024/04/20(六) 15:00～18:00</span></strong></span></p><div style="text-align: justify;"><p><span style="font-size:1.25em"><strong>▌地點</strong></span></p><p><span style="font-size:1.5em"><strong><span style="color:#cc0000">國立清華大學校本部 北門大草坪</span></strong></span></p><div style="text-align: justify;"><p><span style="font-size:1.25em"><strong>▌參加資格</strong></span></p></div><p><span style="font-size:1.5em"><strong><span style="color:#cc0000">國立清華大學畢業校友、應屆畢業生、教職員</span></strong><span style="color:#cc0000">（其餘在校生無需報名，可自由入場）</span></span></p><div style="text-align: justify;"><p style="text-align:justify"><span style="font-size:1.25em"><strong>▌報名票種</strong></span></p></div><div style="text-align: justify;"><p><span style="font-size:1.125em"><strong>每人限取一張票，本中心將進行身分驗證。</strong></span></p><table border="1" cellpadding="1" cellspacing="1" style="width:600px"><tbody><tr><td><span style="font-size:1.125em"><strong>🎟️校友票</strong></span></td><td><span style="font-size:1.125em">適用於清華畢業校友。</span></td></tr><tr><td><span style="font-size:1.125em"><strong>🎟️應屆畢業生票</strong></span></td><td><span style="font-size:1.125em">適用於今年<strong>即將</strong>畢業之在校生。</span></td></tr><tr><td><span style="font-size:1.125em"><strong>🎟️教職員票</strong></span></td><td><span style="font-size:1.125em">適用於清華教職員生。</span></td></tr></tbody></table><p><span style="font-size:1.125em">※ 同時符合不同票種資格者，請【<strong><span style="color:#c0392b">擇一票種</span></strong>】即可。</span></p><div style="text-align: justify;"><p><strong><span style="font-size:1.25em"><strong>▌報名注意事項&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</strong></span></strong></p></div><div style="text-align: justify;"><ol><li style="text-align:justify"><span style="font-size:1.125em">本活動採線上報名，數量有限，額滿為止。<span style="color:#cc0000">（若未事先完成報名，活動當天亦可至現場與大家同樂。）</span></span></li><li style="text-align:justify"><span style="font-size:1.125em"><span style="font-size:1.125em">活動當日憑票至服務台報到，即可獲得<span style="color:#660066"><strong>【清紫野餐禮】</strong>、<strong>【超值餐飲券】</strong>、<strong>【好運摸彩券】</strong></span>。</span></span></li><li style="text-align:justify"><span style="font-size:1.125em"><span style="font-size:1.125em">本中心將於正式活動前一週內發送報名成功通知信予校友。</span></span></li><li style="text-align:justify"><span style="font-size:1.125em"><span style="font-size:1.125em">欲報名親子DIY手作課程者，請於活動當日至各攤位現場報名。</span></span></li><li><span style="font-size:1.125em"><span style="font-size:1.125em">如有任何問題，請洽詢：林小姐 03-5731246，或email至alumni@my.nthu.edu.tw。</span></span></li></ol><div style="text-align: justify;"><span style="font-size:1.125em"><span style="font-size:1.125em">雨備場地：另行公告</span></span></div><div style="text-align: justify;"><span style="font-size:1.125em"><span style="font-size:1.125em">主辦單位：國立清華大學校友服務中心、國立清華大學校友總會</span></span></div><div style="text-align: justify;"><span style="font-size:1.125em"><span style="font-size:1.125em">協辦單位：十二籃市集</span></span></div><div style="text-align: justify;"><hr><h4><strong><span style="font-size:1.125em"><span style="font-size:1.25em"><span style="color:#660066"><span style="color:#660066"><strong><img alt="野餐派對流程" src="https://alumni.site.nthu.edu.tw/var/file/346/1346/img/4506/272720855.png" style="height:96px; width:400px"></strong></span></span></span></span></strong></h4></div><div style="margin-left: 40px; text-align: justify;">&nbsp;</div><p><strong><span style="font-size:1.125em"><img alt="圖片" src="https://alumni.site.nthu.edu.tw/var/file/346/1346/img/4506/540444797.png" style="height:400px; width:800px"></span></strong></p><p style="text-align:justify">&nbsp;</p><h4 style="text-align:justify"><strong><span style="font-size:1.125em"><img alt="活動內容" src="https://alumni.site.nthu.edu.tw/var/file/346/1346/img/4506/497689035.png" style="height:96px; width:400px"></span></strong></h4><p style="text-align:justify"><strong><span style="font-size:1.25em">◤<strong>草地音樂會</strong>◢</span></strong></p><div style="text-align: justify; margin-left: 40px;"><strong><span style="font-size:1.125em"><strong>↯↯ 超強卡司團隊 ╳ 學生社團&nbsp;↯↯&nbsp;</strong></span></strong></div><div style="text-align: justify; margin-left: 40px;"><div><span style="font-size:1.125em">校友中心與校友總會為校友們舉辦草地音樂會，帶大家穿越時空、找回曾經熱血的自己！</span></div><div><span style="font-size:1.125em">無論是畢業幾年的校友，都歡迎攜家帶眷、邀請同窗好友，一同參加清華校園音樂派對<strong>🎵</strong></span></div></div><p style="text-align:justify"><strong><span style="font-size:1.125em">👉演出名單：<a href="https://www.facebook.com/andrewt0604" target="_blank" title="#深情歌手陳勢安"><span style="color:#990000"><strong>#深情歌手陳勢安</strong></span></a>、<a href="https://www.facebook.com/baiannmusic" target="_blank" title="#創作才女白安"><span style="color:#990000"><strong>#創作才女白安</strong></span></a>、<a href="https://www.facebook.com/papunband" target="_blank" title="#龐克搖滾怕胖團"><span style="color:#990000"><strong>#龐克搖滾怕胖團</strong></span></a>、<a href="https://www.instagram.com/eric_lu_music?igsh=aXF0YXN3a2c4cW0=" target="_blank" title="#陽光暖男呂學翰"><span style="color:#990000"><strong>#陽光暖男呂學翰</strong></span></a>、<a href="https://www.facebook.com/JUSTINLINTINGHAN" target="_blank" title="#新生代唱將林亭翰"><span style="color:#990000"><strong>#新生代唱將林亭翰</strong></span></a>及學生社團。</span></strong></p><p style="text-align:justify"><strong><span style="font-size:1.25em">◤<strong>十二籃市集</strong>◢</span></strong></p><div style="text-align: justify; margin-left: 40px;"><div><span style="font-size:1.125em">週末與家人、朋友一同到清華野餐，欣賞著現場音樂會的同時，也可以逛逛草坪周邊的十二籃市集💜</span></div><div><span style="font-size:1.125em">不管是想品嘗悠閒下午茶、逛逛手作服飾，還是體驗手作課程，絕對不能錯過十二籃市集❗❗<strong>🎵</strong></span></div></div><p style="text-align:justify"><strong><span style="font-size:1.125em">👉出席攤位：即將揭曉，敬請期待。</span></strong></p><p style="text-align:justify"><strong><strong><span style="font-size:1.25em">◤<strong>親子DIY活動</strong>◢</span></strong></strong></p><div style="text-align: justify; margin-left: 40px;"><div><span style="font-size:1.125em">今年推出二種手作DIY活動，讓大家從輕鬆簡單的手作活動中，不但可以獲得滿滿的成就感，更可以增進親子間情誼，活動也將開放全時段讓大家來體驗🎀</span></div></div><p style="text-align:justify"><strong><span style="font-size:1.125em">時間：14:00-18:00</span></strong></p><p style="text-align:justify"><strong><span style="font-size:1.125em">地點：路易莎前廣場</span></strong></p><p style="text-align:justify"><strong><span style="font-size:1.125em">報名方式：活動當天請至攤位現場報名。</span></strong></p><h4 style="text-align:justify"><strong><span style="font-size:1.125em"><img alt="清紫野餐禮" src="https://alumni.site.nthu.edu.tw/var/file/346/1346/img/4506/823036492.png" style="height:96px; width:400px"></span><span style="font-size:1.125em">&nbsp;</span></strong></h4><div style="text-align: justify; margin-left: 40px;"><strong><strong><span style="font-size:1.125em"><span style="color:#000000"><span style="background-color:#ffffff"><span style="color:#373737">活動當日，</span></span></span></span><span style="font-size:1.375em"><span style="color:#ff0000"><span style="background-color:#ffffff"><strong>前300名</strong></span></span></span><span style="font-size:1.125em"><span style="color:#000000"><span style="background-color:#ffffff"><span style="color:#373737">報到之校友，即可獲得</span></span></span></span><span style="font-size:1.375em"><span style="color:#ff0000"><span style="background-color:#ffffff"><strong>熊貓手持風扇🐼</strong></span></span></span><span style="font-size:1.125em"><span style="color:#000000"><span style="background-color:#ffffff"><span style="color:#373737">一支，數量有限，贈完為止。</span></span></span></span></strong></strong></div><p><strong><strong><img alt="圖片" src="https://alumni.site.nthu.edu.tw/var/file/346/1346/img/921101575.gif" style="height:450px; width:800px"></strong></strong></p><div style="text-align: justify;"><h4><strong><span style="font-size:1.125em"><span style="font-size:1.25em"><span style="color:#660066"><span style="color:#660066"><strong><img alt="環保一起來" src="https://alumni.site.nthu.edu.tw/var/file/346/1346/img/4506/630128105.png" style="height:96px; width:400px"></strong></span></span></span></span></strong></h4><p style="text-align:justify"><strong><span style="font-size:1.25em">◤<strong>免費環保餐具租借</strong>◢</span></strong></p><p style="text-align:justify"><strong><span style="font-size:1.125em">野餐派對現場將提供環保餐具租借，歡迎前往環保餐具借用攤位進行租借，現場將開放租用環保杯、環保餐盒，邀請大家一起愛地球🙌</span></strong></p><p style="text-align:justify"><span style="font-size:1.125em"><strong>費用：<span style="color:#c0392b">免費</span>，需付押金$100/組。</strong></span></p></div><p><img alt="圖片" src="https://alumni.site.nthu.edu.tw/var/file/346/1346/img/4506/334378129.png" style="height:566px; width:800px"></p><hr><p style="text-align:justify"><strong><span style="font-size:1.25em">其他注意事項：</span></strong></p><ol><li style="text-align:justify"><span style="font-size:1.125em">請注意您填寫的報名資料是否正確，本中心會查證校友身分後，發送報名成功通知信。</span></li><li style="text-align:justify"><span style="font-size:1.125em">您填寫的資料僅做為校務行政、校友服務及校友聯繫等用途，並將遵守個人資料保護法相關規定以維護校友權益。</span></li><li style="text-align:justify"><span style="font-size:1.125em">基於安全考量，活動現場禁止使用火（含卡式爐、瓦斯爐等）或瓦斯，禁飲含有酒精飲料，並全面禁菸；違者將取消活動資格。</span></li><li style="text-align:justify"><span style="font-size:1.125em">為維持活動現場秩序，請配合現場工作人員的指揮。</span></li><li style="text-align:justify"><span style="font-size:1.125em">參加者可自行攜帶野餐墊或易開帳等相關野餐用品，因場地侷限不可搭設天幕帳或小型帳篷，以免影響其他參與者。個人防曬用品請自備。</span></li><li style="text-align:justify"><span style="font-size:1.125em">活動當天不提供物品寄放，貴重物品請自行存放保管，遺失概不負責。</span></li><li style="text-align:justify"><span style="font-size:1.125em">為響應環保並落實減塑行動，請自備環保餐具、餐盒、水杯等用具，或前往環安中心攤位借用環保餐具。</span></li><li style="text-align:justify"><span style="font-size:1.125em">活動當日執行單位將依法為參加者投保公共意外險，有關本身疾患引起之病症不在承保範圍內，敬請特別注意。（所有細節依投保公司之保險契約為準）</span></li><li style="text-align:justify"><span style="font-size:1.125em">主辦單位將視天候因素及報名狀況，保留活動進行及變更之權利。</span></li></ol></div></div></div>',
        '新北市', '板橋區中山路1號', NOW() - INTERVAL 10 DAY,
        NOW() + INTERVAL 40 DAY, NOW() + INTERVAL 40 DAY - INTERVAL 10 HOUR , NOW() + INTERVAL 40 DAY, 1, 5, 1, 0,70),
       ('2024清華校慶校友活動—清紫野餐派對x草地音樂會', 2, 4,
        '<p><span style="font-size:1.5em"><strong><u><span style="color:#cc0000">3/15(五)下午1:00起</span></u>開放報名，截止至票券全數售完為止。</strong></span></p><p><img alt="圖片" src="https://alumni.site.nthu.edu.tw/var/file/346/1346/img/817647729.png" style="height:400px; width:800px"></p><h2 style="text-align:center"><span style="font-size:1.625em"><strong>╭。☆║2024清紫野餐派對 ✘ 草地音樂會來啦║☆。╮ </strong></span></h2><p style="text-align:center"><span style="font-size:1.25em"><strong>為慶祝國立清華大學創校 113 週年暨在臺建校 68 週年生日快樂</strong></span></p><p style="text-align:center"><span style="font-size:1.25em"><strong>校友中心、校友總會邀請各地清華人，一起回母校同樂</strong></span></p><p style="text-align:center"><span style="font-size:1.25em"><strong>活動當天將有high翻全場的歌手表演、學生社團表演，及豐富多元的市集、動手DIY</strong></span></p><p style="text-align:center"><span style="font-size:1.25em"><strong>最重要的是，絕對不能錯過精彩的抽獎摸彩活動🎁</strong></span></p><hr><p style="text-align:justify"><span style="font-size:1.25em"><strong>▌日期</strong></span></p><p><span style="font-size:1.5em"><strong><span style="color:#cc0000">2024/04/20(六) 15:00～18:00</span></strong></span></p><div style="text-align: justify;"><p><span style="font-size:1.25em"><strong>▌地點</strong></span></p><p><span style="font-size:1.5em"><strong><span style="color:#cc0000">國立清華大學校本部 北門大草坪</span></strong></span></p><div style="text-align: justify;"><p><span style="font-size:1.25em"><strong>▌參加資格</strong></span></p></div><p><span style="font-size:1.5em"><strong><span style="color:#cc0000">國立清華大學畢業校友、應屆畢業生、教職員</span></strong><span style="color:#cc0000">（其餘在校生無需報名，可自由入場）</span></span></p><div style="text-align: justify;"><p style="text-align:justify"><span style="font-size:1.25em"><strong>▌報名票種</strong></span></p></div><div style="text-align: justify;"><p><span style="font-size:1.125em"><strong>每人限取一張票，本中心將進行身分驗證。</strong></span></p><table border="1" cellpadding="1" cellspacing="1" style="width:600px"><tbody><tr><td><span style="font-size:1.125em"><strong>🎟️校友票</strong></span></td><td><span style="font-size:1.125em">適用於清華畢業校友。</span></td></tr><tr><td><span style="font-size:1.125em"><strong>🎟️應屆畢業生票</strong></span></td><td><span style="font-size:1.125em">適用於今年<strong>即將</strong>畢業之在校生。</span></td></tr><tr><td><span style="font-size:1.125em"><strong>🎟️教職員票</strong></span></td><td><span style="font-size:1.125em">適用於清華教職員生。</span></td></tr></tbody></table><p><span style="font-size:1.125em">※ 同時符合不同票種資格者，請【<strong><span style="color:#c0392b">擇一票種</span></strong>】即可。</span></p><div style="text-align: justify;"><p><strong><span style="font-size:1.25em"><strong>▌報名注意事項&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</strong></span></strong></p></div><div style="text-align: justify;"><ol><li style="text-align:justify"><span style="font-size:1.125em">本活動採線上報名，數量有限，額滿為止。<span style="color:#cc0000">（若未事先完成報名，活動當天亦可至現場與大家同樂。）</span></span></li><li style="text-align:justify"><span style="font-size:1.125em"><span style="font-size:1.125em">活動當日憑票至服務台報到，即可獲得<span style="color:#660066"><strong>【清紫野餐禮】</strong>、<strong>【超值餐飲券】</strong>、<strong>【好運摸彩券】</strong></span>。</span></span></li><li style="text-align:justify"><span style="font-size:1.125em"><span style="font-size:1.125em">本中心將於正式活動前一週內發送報名成功通知信予校友。</span></span></li><li style="text-align:justify"><span style="font-size:1.125em"><span style="font-size:1.125em">欲報名親子DIY手作課程者，請於活動當日至各攤位現場報名。</span></span></li><li><span style="font-size:1.125em"><span style="font-size:1.125em">如有任何問題，請洽詢：林小姐 03-5731246，或email至alumni@my.nthu.edu.tw。</span></span></li></ol><div style="text-align: justify;"><span style="font-size:1.125em"><span style="font-size:1.125em">雨備場地：另行公告</span></span></div><div style="text-align: justify;"><span style="font-size:1.125em"><span style="font-size:1.125em">主辦單位：國立清華大學校友服務中心、國立清華大學校友總會</span></span></div><div style="text-align: justify;"><span style="font-size:1.125em"><span style="font-size:1.125em">協辦單位：十二籃市集</span></span></div><div style="text-align: justify;"><hr><h4><strong><span style="font-size:1.125em"><span style="font-size:1.25em"><span style="color:#660066"><span style="color:#660066"><strong><img alt="野餐派對流程" src="https://alumni.site.nthu.edu.tw/var/file/346/1346/img/4506/272720855.png" style="height:96px; width:400px"></strong></span></span></span></span></strong></h4></div><div style="margin-left: 40px; text-align: justify;">&nbsp;</div><p><strong><span style="font-size:1.125em"><img alt="圖片" src="https://alumni.site.nthu.edu.tw/var/file/346/1346/img/4506/540444797.png" style="height:400px; width:800px"></span></strong></p><p style="text-align:justify">&nbsp;</p><h4 style="text-align:justify"><strong><span style="font-size:1.125em"><img alt="活動內容" src="https://alumni.site.nthu.edu.tw/var/file/346/1346/img/4506/497689035.png" style="height:96px; width:400px"></span></strong></h4><p style="text-align:justify"><strong><span style="font-size:1.25em">◤<strong>草地音樂會</strong>◢</span></strong></p><div style="text-align: justify; margin-left: 40px;"><strong><span style="font-size:1.125em"><strong>↯↯ 超強卡司團隊 ╳ 學生社團&nbsp;↯↯&nbsp;</strong></span></strong></div><div style="text-align: justify; margin-left: 40px;"><div><span style="font-size:1.125em">校友中心與校友總會為校友們舉辦草地音樂會，帶大家穿越時空、找回曾經熱血的自己！</span></div><div><span style="font-size:1.125em">無論是畢業幾年的校友，都歡迎攜家帶眷、邀請同窗好友，一同參加清華校園音樂派對<strong>🎵</strong></span></div></div><p style="text-align:justify"><strong><span style="font-size:1.125em">👉演出名單：<a href="https://www.facebook.com/andrewt0604" target="_blank" title="#深情歌手陳勢安"><span style="color:#990000"><strong>#深情歌手陳勢安</strong></span></a>、<a href="https://www.facebook.com/baiannmusic" target="_blank" title="#創作才女白安"><span style="color:#990000"><strong>#創作才女白安</strong></span></a>、<a href="https://www.facebook.com/papunband" target="_blank" title="#龐克搖滾怕胖團"><span style="color:#990000"><strong>#龐克搖滾怕胖團</strong></span></a>、<a href="https://www.instagram.com/eric_lu_music?igsh=aXF0YXN3a2c4cW0=" target="_blank" title="#陽光暖男呂學翰"><span style="color:#990000"><strong>#陽光暖男呂學翰</strong></span></a>、<a href="https://www.facebook.com/JUSTINLINTINGHAN" target="_blank" title="#新生代唱將林亭翰"><span style="color:#990000"><strong>#新生代唱將林亭翰</strong></span></a>及學生社團。</span></strong></p><p style="text-align:justify"><strong><span style="font-size:1.25em">◤<strong>十二籃市集</strong>◢</span></strong></p><div style="text-align: justify; margin-left: 40px;"><div><span style="font-size:1.125em">週末與家人、朋友一同到清華野餐，欣賞著現場音樂會的同時，也可以逛逛草坪周邊的十二籃市集💜</span></div><div><span style="font-size:1.125em">不管是想品嘗悠閒下午茶、逛逛手作服飾，還是體驗手作課程，絕對不能錯過十二籃市集❗❗<strong>🎵</strong></span></div></div><p style="text-align:justify"><strong><span style="font-size:1.125em">👉出席攤位：即將揭曉，敬請期待。</span></strong></p><p style="text-align:justify"><strong><strong><span style="font-size:1.25em">◤<strong>親子DIY活動</strong>◢</span></strong></strong></p><div style="text-align: justify; margin-left: 40px;"><div><span style="font-size:1.125em">今年推出二種手作DIY活動，讓大家從輕鬆簡單的手作活動中，不但可以獲得滿滿的成就感，更可以增進親子間情誼，活動也將開放全時段讓大家來體驗🎀</span></div></div><p style="text-align:justify"><strong><span style="font-size:1.125em">時間：14:00-18:00</span></strong></p><p style="text-align:justify"><strong><span style="font-size:1.125em">地點：路易莎前廣場</span></strong></p><p style="text-align:justify"><strong><span style="font-size:1.125em">報名方式：活動當天請至攤位現場報名。</span></strong></p><h4 style="text-align:justify"><strong><span style="font-size:1.125em"><img alt="清紫野餐禮" src="https://alumni.site.nthu.edu.tw/var/file/346/1346/img/4506/823036492.png" style="height:96px; width:400px"></span><span style="font-size:1.125em">&nbsp;</span></strong></h4><div style="text-align: justify; margin-left: 40px;"><strong><strong><span style="font-size:1.125em"><span style="color:#000000"><span style="background-color:#ffffff"><span style="color:#373737">活動當日，</span></span></span></span><span style="font-size:1.375em"><span style="color:#ff0000"><span style="background-color:#ffffff"><strong>前300名</strong></span></span></span><span style="font-size:1.125em"><span style="color:#000000"><span style="background-color:#ffffff"><span style="color:#373737">報到之校友，即可獲得</span></span></span></span><span style="font-size:1.375em"><span style="color:#ff0000"><span style="background-color:#ffffff"><strong>熊貓手持風扇🐼</strong></span></span></span><span style="font-size:1.125em"><span style="color:#000000"><span style="background-color:#ffffff"><span style="color:#373737">一支，數量有限，贈完為止。</span></span></span></span></strong></strong></div><p><strong><strong><img alt="圖片" src="https://alumni.site.nthu.edu.tw/var/file/346/1346/img/921101575.gif" style="height:450px; width:800px"></strong></strong></p><div style="text-align: justify;"><h4><strong><span style="font-size:1.125em"><span style="font-size:1.25em"><span style="color:#660066"><span style="color:#660066"><strong><img alt="環保一起來" src="https://alumni.site.nthu.edu.tw/var/file/346/1346/img/4506/630128105.png" style="height:96px; width:400px"></strong></span></span></span></span></strong></h4><p style="text-align:justify"><strong><span style="font-size:1.25em">◤<strong>免費環保餐具租借</strong>◢</span></strong></p><p style="text-align:justify"><strong><span style="font-size:1.125em">野餐派對現場將提供環保餐具租借，歡迎前往環保餐具借用攤位進行租借，現場將開放租用環保杯、環保餐盒，邀請大家一起愛地球🙌</span></strong></p><p style="text-align:justify"><span style="font-size:1.125em"><strong>費用：<span style="color:#c0392b">免費</span>，需付押金$100/組。</strong></span></p></div><p><img alt="圖片" src="https://alumni.site.nthu.edu.tw/var/file/346/1346/img/4506/334378129.png" style="height:566px; width:800px"></p><hr><p style="text-align:justify"><strong><span style="font-size:1.25em">其他注意事項：</span></strong></p><ol><li style="text-align:justify"><span style="font-size:1.125em">請注意您填寫的報名資料是否正確，本中心會查證校友身分後，發送報名成功通知信。</span></li><li style="text-align:justify"><span style="font-size:1.125em">您填寫的資料僅做為校務行政、校友服務及校友聯繫等用途，並將遵守個人資料保護法相關規定以維護校友權益。</span></li><li style="text-align:justify"><span style="font-size:1.125em">基於安全考量，活動現場禁止使用火（含卡式爐、瓦斯爐等）或瓦斯，禁飲含有酒精飲料，並全面禁菸；違者將取消活動資格。</span></li><li style="text-align:justify"><span style="font-size:1.125em">為維持活動現場秩序，請配合現場工作人員的指揮。</span></li><li style="text-align:justify"><span style="font-size:1.125em">參加者可自行攜帶野餐墊或易開帳等相關野餐用品，因場地侷限不可搭設天幕帳或小型帳篷，以免影響其他參與者。個人防曬用品請自備。</span></li><li style="text-align:justify"><span style="font-size:1.125em">活動當天不提供物品寄放，貴重物品請自行存放保管，遺失概不負責。</span></li><li style="text-align:justify"><span style="font-size:1.125em">為響應環保並落實減塑行動，請自備環保餐具、餐盒、水杯等用具，或前往環安中心攤位借用環保餐具。</span></li><li style="text-align:justify"><span style="font-size:1.125em">活動當日執行單位將依法為參加者投保公共意外險，有關本身疾患引起之病症不在承保範圍內，敬請特別注意。（所有細節依投保公司之保險契約為準）</span></li><li style="text-align:justify"><span style="font-size:1.125em">主辦單位將視天候因素及報名狀況，保留活動進行及變更之權利。</span></li></ol></div></div></div>',
        '桃園市', '中壢區中大路300號', NOW() - INTERVAL 25 DAY,
        NOW() + INTERVAL 40 DAY, NOW() + INTERVAL 40 DAY - INTERVAL 10 HOUR , NOW() + INTERVAL 40 DAY, 1,
        5, 1, 0,0),
       ('台北新藝術博覽會', 4, 5,
        '<p>台北新藝術博覽會&nbsp;<a href="https://www.arts.org.tw/">https://www.arts.org.tw/</a>&nbsp;是亞洲國際藝術展會的新引領者，由「社團法人台灣國際當代藝術家協會」舉辦。每年，這場盛會吸引來自世界七十多個國家的近五百位藝術家參與。它不同於其他全球藝術博覽會，不再以畫廊為主，勇於嘗試創新，率先提出「以藝術家為核心」的策展理念。2024年的盛會將於4月26至28日在台北世貿一館盛大舉行！</p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2402220904361970801827.jpg" style="height:178px; width:400px"></p><p>2024年的台北新藝術博覽會由藝術總監李善單教授精心挑選作品，並細心策劃九大展區，包括大會策展區、國際當代藝術、紐約當代藝術基金、國際藝術家沙龍大展、中國當代藝術、台灣當代藝術、國際藝術家大獎賽、文創藝術與藝出慈悲。這些區域將呈現各種媒材、蘊含不同文化內涵的國際名家作品。</p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2402220906252057679264.jpg" style="height:197px; width:350px"></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2402220907251713743248.jpg" style="height:233px; width:350px"></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2402220905451796126273.jpg" style="height:233px; width:350px"></p><p>其中，「藝出慈悲‧百大名人」義賣活動是一項融合藝術和慈善的重要舉措，吸引數百位名人無償貢獻作品，帶動大眾參與支援社會弱勢群體。同時，這也是培養大眾對藝術品收藏興趣的一個重要途徑，這個活動具有深遠的意義，歷屆都受到廣泛關注，所有作品都被搶購一空！</p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2402220908244495111150.jpg" style="height:210px; width:350px"></p><p>台北新藝術博覽會不再套用全球藝博會以畫廊為主的模式，而是首創以「藝術家為核心」的全新概念。在藝術總監李善單的巧妙規劃下，展場設計完全以藝術家為中心，每個展位都像是一位藝術家的獨立展覽。這種細膩打造的特色不僅贏得業界好評，更讓台北新藝術博覽會成為「具有溫度」的國際藝術盛事。經過十年的辛勤努力，台北新藝術博覽會已成為亞洲最具代表性和規模性的藝術展會。</p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2402220909295133012790.jpg" style="height:233px; width:350px"></p><p>&nbsp;</p><p><iframe allow=";" allowfullscreen="" frameborder="0" height="360" src="https://www.youtube.com/embed/japVv2vTWi8?start=7" width="640"></iframe></p>',
        '台中市', '西區台灣大道二段2號', '2024-4-5 00:00:00',
        '2024-9-19 12:00:00', '2024-9-19 06:00:00', '2024-9-19 12:00:00', 1,
        5, 1, 0,40),
       ('台北新藝術博覽會', 4, 5,
        '<p>台北新藝術博覽會&nbsp;<a href="https://www.arts.org.tw/">https://www.arts.org.tw/</a>&nbsp;是亞洲國際藝術展會的新引領者，由「社團法人台灣國際當代藝術家協會」舉辦。每年，這場盛會吸引來自世界七十多個國家的近五百位藝術家參與。它不同於其他全球藝術博覽會，不再以畫廊為主，勇於嘗試創新，率先提出「以藝術家為核心」的策展理念。2024年的盛會將於4月26至28日在台北世貿一館盛大舉行！</p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2402220904361970801827.jpg" style="height:178px; width:400px"></p><p>2024年的台北新藝術博覽會由藝術總監李善單教授精心挑選作品，並細心策劃九大展區，包括大會策展區、國際當代藝術、紐約當代藝術基金、國際藝術家沙龍大展、中國當代藝術、台灣當代藝術、國際藝術家大獎賽、文創藝術與藝出慈悲。這些區域將呈現各種媒材、蘊含不同文化內涵的國際名家作品。</p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2402220906252057679264.jpg" style="height:197px; width:350px"></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2402220907251713743248.jpg" style="height:233px; width:350px"></p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2402220905451796126273.jpg" style="height:233px; width:350px"></p><p>其中，「藝出慈悲‧百大名人」義賣活動是一項融合藝術和慈善的重要舉措，吸引數百位名人無償貢獻作品，帶動大眾參與支援社會弱勢群體。同時，這也是培養大眾對藝術品收藏興趣的一個重要途徑，這個活動具有深遠的意義，歷屆都受到廣泛關注，所有作品都被搶購一空！</p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2402220908244495111150.jpg" style="height:210px; width:350px"></p><p>台北新藝術博覽會不再套用全球藝博會以畫廊為主的模式，而是首創以「藝術家為核心」的全新概念。在藝術總監李善單的巧妙規劃下，展場設計完全以藝術家為中心，每個展位都像是一位藝術家的獨立展覽。這種細膩打造的特色不僅贏得業界好評，更讓台北新藝術博覽會成為「具有溫度」的國際藝術盛事。經過十年的辛勤努力，台北新藝術博覽會已成為亞洲最具代表性和規模性的藝術展會。</p><p style="text-align:center"><img alt="" src="https://static.accupass.com/eventintro/2402220909295133012790.jpg" style="height:233px; width:350px"></p><p>&nbsp;</p><p><iframe allow=";" allowfullscreen="" frameborder="0" height="360" src="https://www.youtube.com/embed/japVv2vTWi8?start=7" width="640"></iframe></p>',
        '台中市', '西區台灣大道二段2號', '2024-4-5 00:00:00',
        '2024-9-20 12:00:00', '2024-9-20 06:00:00', '2024-9-20 12:00:00', 1,
        5, 1, 0,0),
       ('2024中華奧會第2屆小小運動會', 5, 6,
        '<p>2024中華奧會第2屆小小運動會為向下紮根推廣全民體育運動風氣，藉由呈現奧運會儀式精神，</p><p>以及多樣運動項目包括田徑、跆拳道、羽球、體操、足球、射擊、射箭、拳擊、舉重、擊劍、</p><p>籃球、霹靂舞...等，以趣味設置與參與內容，吸引幼兒與家長族群參與，藉此傳遞奧林匹克格言</p><p>「更高、更快、更強、更團結」的運動價值給全民與社會，促進全民從小養成運動習慣，</p><p>並從活動中感受運動樂趣，提升我國社會未來之持續並穩定運動的人口。</p><hr><p>一、 &nbsp;活動名稱 ： 奧林匹克日系列活動-2024年中華奧會第2屆小小英雄運動會</p><p>二、 &nbsp;指導單位 ： 教育部體育署 、 臺北市議會</p><p>三、 &nbsp;主辦單位 ： 中華奧林匹克委員會</p><p>四、 &nbsp;共同主辦單位 ： 臺北市政府體育局</p><p>五、 &nbsp;承辦單位 ： 中華奧林匹克委員會全民運動委員會</p><p>六、 &nbsp;活動日期 ： 2024年5月11日(六)至5月12日(日)</p><p>七、 &nbsp;活動會場 ： 臺北和平籃球館(台北市大安區敦南街76巷28號)</p><p>八、 &nbsp;報名期間 ： 即日起至4月28日(日)止，若報名額滿將提前停止接受報名</p><p>九、 &nbsp;報名費用（物資包） ： 200元 / 每位3-6歲參加幼童</p><p>十、 報名規範 ： &nbsp; &nbsp; &nbsp;</p><p>&nbsp; &nbsp; &nbsp; &nbsp; 1.依報名人數分為：一般個人報名及團體報名（每一團體以15-20人為計）</p><p>&nbsp; &nbsp; &nbsp; &nbsp; 2.依梯次時段分為（詳請見 梯次說明）：5月11日上午開幕式梯次、5月11日下午第二梯次、</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;5月12日上午第三梯次、5月12日下午第四梯次 &nbsp; &nbsp; &nbsp;</p><p>&nbsp; &nbsp; &nbsp; &nbsp; 3.參加對象：3-6 歲幼童 、 家長親友及幼兒園人員 &nbsp; &nbsp; &nbsp;</p><p>&nbsp; &nbsp; &nbsp; &nbsp; 4.主場館競賽項目：羽球 / 體操 / 跆拳道 / 田徑 / 迷你足球</p><p>&nbsp; &nbsp; &nbsp; &nbsp; 🔺5月11日下午第二梯次無足球項目</p><p>&nbsp; &nbsp; &nbsp; &nbsp; 🔺報名時須選擇一項作為報名參與，活動當日將依據報名之順位，以報名優先之項目為主</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;優先安排並將視其他報名各項目之人數，機動調整進行開放參與。 &nbsp; &nbsp; &nbsp;</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;5.同行規範：家長須簽立參與活動之同意書，每位報名者依報名簡章第十二條說明皆須有</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 規定之成年者（監護人）陪同。 &nbsp; &nbsp; &nbsp;</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;6.現場管制範圍 ：</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;🔺【主場館競賽項目】可參與者 ： 完成報名之幼童（依據報名通知之項目參與）</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;🔺【主場館競賽項目】可入場者 ： 報名之同行者（僅能陪同、不開放參與）</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;🔺【環廊區】及【觀眾座位區】 ： 開放予參與幼童、同行者以及一般民眾皆可</p><p>十一、 報名梯次說明 ：🔺本次活動除【主場館競賽項目】需具備報名資格，其他區域包含</p><p>&nbsp; &nbsp; &nbsp; &nbsp; 【環廊區】及【觀眾座位區】皆開放一般民眾入場入場</p><p><img alt="" src="https://static.accupass.com/eventintro/2404010512546692920570.jpg" style="height:859px; width:750px"></p><p>十二、 &nbsp;時程規劃 ：🔺【主場館競賽項目】依第十一項第四條說明，須以報名時選擇之項目參與</p><p><img alt="" src="https://static.accupass.com/eventintro/2403261009127218562420.jpg" style="height:1124px; width:750px"></p><p>十三、 &nbsp;報名方式 ：</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;1.本活動以網路報名：可選擇個人報名或團體報名（15位3-6歲幼童含以上即可以團體報名）</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;2.報名時請評估幼童年齡、選擇報名梯次以及評估陪同參與人員</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;3.報名日截止前得更換人名與活動衣尺寸等資料，請電聯活動小組</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 02-8768-1866#570王先生，請注意報名日截止後即無法變更任何資料</p><p>十四、 報名禮贈品說明： &nbsp; &nbsp; &nbsp;</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;1.凡報名之幼童，於報到時即可領取報到禮；內容物包含：</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;🔵 2024年中華奧會第2屆小小英雄運動會 紀念衣</p><p><img alt="" src="https://static.accupass.com/eventintro/2404090939289595619480.jpg" style="height:292px; width:700px"></p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;🔵 2024年中華奧會第2屆小小英雄運動會 專屬選手證（含紀念證件帶） &nbsp; &nbsp; &nbsp;</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;2.配戴選手證之3-6歲幼童可參與報名之【主場館競賽項目】項目，完成運動參與，</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;即可獲得英雄物資包，內容物包含：</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;🔵 2024年中華奧會第2屆小小英雄運動會 紀念束口袋</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;🔵 2024年中華奧會第2屆小小英雄運動會 紀念獎牌</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;3.配戴選手證之3-6歲幼童以及同行證之孩童完成【環廊區】項目中中華奧會展區+</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;打卡拍照任務+2項【環廊區】項目即可獲得闖關禮，內容物包含：</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;🔵 2024年中華奧會第2屆小小英雄運動會 紀念貼紙</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;🔵 2024年中華奧會第2屆小小英雄運動會 紀念徽章</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;4.未報名之民眾（不限年齡），完成【環廊區】項目中中華奧會展區+打卡拍照任務+</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;2項【環廊區】項目即可獲得</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;🔵 2024年中華奧會第2屆小小英雄運動會 紀念貼紙</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;5.配戴選手證之3-6歲幼童，完成報名之【主場館競賽項目】項目及【環廊區】</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;全項目參與，即可參加加碼抽獎活動，抽獎禮項目以報名網頁公告為主</p><p>十五、 報名注意事項： &nbsp; &nbsp; &nbsp;</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;1.手續不全、報名費繳交不足者，均以未完成報名手續處理</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;2.退件所衍生之費用將由報名者自行負擔，請自行至報名系統上查詢繳款狀況</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;3.本活動場地位於室內，若下雨則照常舉行</p><p>十六、 報名規範：</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;1.主辦單位、協辦單位、贊助單位有權自由使用本次活動中所拍攝之照片與錄影影片</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;(包含後製、合成)並於任何場所展出刊登，報名者(家長)報名繳費即表示同意其肖像權</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;供上述單位可用於其他活動相關之宣傳刊登，且照片與錄影影片版權歸主辦單位所有，</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;報名者(家長)事後不得追究肖像權；報名者(家長)不得將本次活動主辦單位拍攝之照片</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;與錄影影片(包含後製、合成)使用於商業行為上</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;2.主辦單位、協辦單位、贊助單位已購買公共意外責任險，如於活動中發生意外事故，</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;將由保險公司進行後續處理，本身有疾病者請評估是否參加，若發生事故參加者</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;不得向其他參加活動人員、主辦單位、場地單位、指導單位或相關人員追究責任</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;3.參與活動時，報名者(家長)若未依主辦單位規定及現場工作人員指示，因而導致</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;意外受傷、財物損失或死亡者，皆不得向主辦單位、協辦單位、執行單位、指導單位，</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;場地單位要求任何形式之賠償</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;4.報名並完成繳費後即同意本報名規範，如不同意，請勿報名</p><p>十七、 退費說明：🔺請報名前知悉並同意本項目條款再進行報名🔺</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;1.於活動報名截止日2024/04/28 23:59:59前可申請退費，</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;報名截止期限之後恕無法申請退費</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;2.申請退費者請至活動主辦服務信箱（tpenockidsgames@gmail.com）登記，</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;來信內容請註明報名者訂單編號、姓名、身份證字號、聯絡電話，並附上須退款帳戶資料</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;（請提供銀行名稱、分行名稱、帳號、戶名等資料），確認報名者身份與資料相符後，</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;將陸續辦理退款，退費者同時失去參賽資格。 (申請退費需自行承擔匯款手續費)</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;3.自行轉讓名額者，如有任何意外發生參賽者應負連帶保險理賠及法律責任</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;4.如活動遇不可抗力之因素，如颱風、地震、水災、或因中央/地方行政單位公告無法舉辦/</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;建議取消「大型群聚活動」等因素，為顧及參與者的安全，大會有權以簡訊通知及官方</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;中華奧會粉專公告活動取消或順延；若造成活動取消，大會將於官方粉絲專頁公告且</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;全額退款，但不於事後寄送已製作之紀念品，敬請見諒</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;5.參與者於報名截止日之後申請取消報名即退費者，主辦單位得不退還報名費。但參與者</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;因下列事由未能參與活動者，得檢附證明文件經主辦單位認可，向主辦單位申請取消報名</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;及退費，主辦單位應將全額退還報名費：</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;🔺成年家屬陪同者兵役或點閱、教育召集</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;🔺配合中央/地方行政單位公告不得參與大型群聚活動者</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;🔺其他因不可抗力且無法歸責於報名者之重大事故，並經主辦單位審核認可。 &nbsp; &nbsp; &nbsp;</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;6.若有任何報名、退費問題請電洽 02-8768-1866#570王先生，並轉接至小小英雄運動會</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;活動專案小組；服務時間：週一至週五10：00 - 18：00</p><p>&nbsp;</p><p>本活動辦法若有未盡詳細事宜，主辦單位得隨時修訂並保留修改活動內容細節之權利，且不另行通知&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>',
        '台南市', '東區中華東路1段1號', '2024-4-6 00:00:00',
        '2024-10-20 12:00:00', '2024-10-20 06:00:00', '2024-10-20 12:00:00', 1,
        5, 1, 0,25),
       ('2024中華奧會第2屆小小運動會', 5, 6,
        '<p>2024中華奧會第2屆小小運動會為向下紮根推廣全民體育運動風氣，藉由呈現奧運會儀式精神，</p><p>以及多樣運動項目包括田徑、跆拳道、羽球、體操、足球、射擊、射箭、拳擊、舉重、擊劍、</p><p>籃球、霹靂舞...等，以趣味設置與參與內容，吸引幼兒與家長族群參與，藉此傳遞奧林匹克格言</p><p>「更高、更快、更強、更團結」的運動價值給全民與社會，促進全民從小養成運動習慣，</p><p>並從活動中感受運動樂趣，提升我國社會未來之持續並穩定運動的人口。</p><hr><p>一、 &nbsp;活動名稱 ： 奧林匹克日系列活動-2024年中華奧會第2屆小小英雄運動會</p><p>二、 &nbsp;指導單位 ： 教育部體育署 、 臺北市議會</p><p>三、 &nbsp;主辦單位 ： 中華奧林匹克委員會</p><p>四、 &nbsp;共同主辦單位 ： 臺北市政府體育局</p><p>五、 &nbsp;承辦單位 ： 中華奧林匹克委員會全民運動委員會</p><p>六、 &nbsp;活動日期 ： 2024年5月11日(六)至5月12日(日)</p><p>七、 &nbsp;活動會場 ： 臺北和平籃球館(台北市大安區敦南街76巷28號)</p><p>八、 &nbsp;報名期間 ： 即日起至4月28日(日)止，若報名額滿將提前停止接受報名</p><p>九、 &nbsp;報名費用（物資包） ： 200元 / 每位3-6歲參加幼童</p><p>十、 報名規範 ： &nbsp; &nbsp; &nbsp;</p><p>&nbsp; &nbsp; &nbsp; &nbsp; 1.依報名人數分為：一般個人報名及團體報名（每一團體以15-20人為計）</p><p>&nbsp; &nbsp; &nbsp; &nbsp; 2.依梯次時段分為（詳請見 梯次說明）：5月11日上午開幕式梯次、5月11日下午第二梯次、</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;5月12日上午第三梯次、5月12日下午第四梯次 &nbsp; &nbsp; &nbsp;</p><p>&nbsp; &nbsp; &nbsp; &nbsp; 3.參加對象：3-6 歲幼童 、 家長親友及幼兒園人員 &nbsp; &nbsp; &nbsp;</p><p>&nbsp; &nbsp; &nbsp; &nbsp; 4.主場館競賽項目：羽球 / 體操 / 跆拳道 / 田徑 / 迷你足球</p><p>&nbsp; &nbsp; &nbsp; &nbsp; 🔺5月11日下午第二梯次無足球項目</p><p>&nbsp; &nbsp; &nbsp; &nbsp; 🔺報名時須選擇一項作為報名參與，活動當日將依據報名之順位，以報名優先之項目為主</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;優先安排並將視其他報名各項目之人數，機動調整進行開放參與。 &nbsp; &nbsp; &nbsp;</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;5.同行規範：家長須簽立參與活動之同意書，每位報名者依報名簡章第十二條說明皆須有</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 規定之成年者（監護人）陪同。 &nbsp; &nbsp; &nbsp;</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;6.現場管制範圍 ：</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;🔺【主場館競賽項目】可參與者 ： 完成報名之幼童（依據報名通知之項目參與）</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;🔺【主場館競賽項目】可入場者 ： 報名之同行者（僅能陪同、不開放參與）</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;🔺【環廊區】及【觀眾座位區】 ： 開放予參與幼童、同行者以及一般民眾皆可</p><p>十一、 報名梯次說明 ：🔺本次活動除【主場館競賽項目】需具備報名資格，其他區域包含</p><p>&nbsp; &nbsp; &nbsp; &nbsp; 【環廊區】及【觀眾座位區】皆開放一般民眾入場入場</p><p><img alt="" src="https://static.accupass.com/eventintro/2404010512546692920570.jpg" style="height:859px; width:750px"></p><p>十二、 &nbsp;時程規劃 ：🔺【主場館競賽項目】依第十一項第四條說明，須以報名時選擇之項目參與</p><p><img alt="" src="https://static.accupass.com/eventintro/2403261009127218562420.jpg" style="height:1124px; width:750px"></p><p>十三、 &nbsp;報名方式 ：</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;1.本活動以網路報名：可選擇個人報名或團體報名（15位3-6歲幼童含以上即可以團體報名）</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;2.報名時請評估幼童年齡、選擇報名梯次以及評估陪同參與人員</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;3.報名日截止前得更換人名與活動衣尺寸等資料，請電聯活動小組</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 02-8768-1866#570王先生，請注意報名日截止後即無法變更任何資料</p><p>十四、 報名禮贈品說明： &nbsp; &nbsp; &nbsp;</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;1.凡報名之幼童，於報到時即可領取報到禮；內容物包含：</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;🔵 2024年中華奧會第2屆小小英雄運動會 紀念衣</p><p><img alt="" src="https://static.accupass.com/eventintro/2404090939289595619480.jpg" style="height:292px; width:700px"></p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;🔵 2024年中華奧會第2屆小小英雄運動會 專屬選手證（含紀念證件帶） &nbsp; &nbsp; &nbsp;</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;2.配戴選手證之3-6歲幼童可參與報名之【主場館競賽項目】項目，完成運動參與，</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;即可獲得英雄物資包，內容物包含：</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;🔵 2024年中華奧會第2屆小小英雄運動會 紀念束口袋</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;🔵 2024年中華奧會第2屆小小英雄運動會 紀念獎牌</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;3.配戴選手證之3-6歲幼童以及同行證之孩童完成【環廊區】項目中中華奧會展區+</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;打卡拍照任務+2項【環廊區】項目即可獲得闖關禮，內容物包含：</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;🔵 2024年中華奧會第2屆小小英雄運動會 紀念貼紙</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;🔵 2024年中華奧會第2屆小小英雄運動會 紀念徽章</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;4.未報名之民眾（不限年齡），完成【環廊區】項目中中華奧會展區+打卡拍照任務+</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;2項【環廊區】項目即可獲得</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;🔵 2024年中華奧會第2屆小小英雄運動會 紀念貼紙</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;5.配戴選手證之3-6歲幼童，完成報名之【主場館競賽項目】項目及【環廊區】</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;全項目參與，即可參加加碼抽獎活動，抽獎禮項目以報名網頁公告為主</p><p>十五、 報名注意事項： &nbsp; &nbsp; &nbsp;</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;1.手續不全、報名費繳交不足者，均以未完成報名手續處理</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;2.退件所衍生之費用將由報名者自行負擔，請自行至報名系統上查詢繳款狀況</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;3.本活動場地位於室內，若下雨則照常舉行</p><p>十六、 報名規範：</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;1.主辦單位、協辦單位、贊助單位有權自由使用本次活動中所拍攝之照片與錄影影片</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;(包含後製、合成)並於任何場所展出刊登，報名者(家長)報名繳費即表示同意其肖像權</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;供上述單位可用於其他活動相關之宣傳刊登，且照片與錄影影片版權歸主辦單位所有，</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;報名者(家長)事後不得追究肖像權；報名者(家長)不得將本次活動主辦單位拍攝之照片</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;與錄影影片(包含後製、合成)使用於商業行為上</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;2.主辦單位、協辦單位、贊助單位已購買公共意外責任險，如於活動中發生意外事故，</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;將由保險公司進行後續處理，本身有疾病者請評估是否參加，若發生事故參加者</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;不得向其他參加活動人員、主辦單位、場地單位、指導單位或相關人員追究責任</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;3.參與活動時，報名者(家長)若未依主辦單位規定及現場工作人員指示，因而導致</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;意外受傷、財物損失或死亡者，皆不得向主辦單位、協辦單位、執行單位、指導單位，</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;場地單位要求任何形式之賠償</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;4.報名並完成繳費後即同意本報名規範，如不同意，請勿報名</p><p>十七、 退費說明：🔺請報名前知悉並同意本項目條款再進行報名🔺</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;1.於活動報名截止日2024/04/28 23:59:59前可申請退費，</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;報名截止期限之後恕無法申請退費</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;2.申請退費者請至活動主辦服務信箱（tpenockidsgames@gmail.com）登記，</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;來信內容請註明報名者訂單編號、姓名、身份證字號、聯絡電話，並附上須退款帳戶資料</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;（請提供銀行名稱、分行名稱、帳號、戶名等資料），確認報名者身份與資料相符後，</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;將陸續辦理退款，退費者同時失去參賽資格。 (申請退費需自行承擔匯款手續費)</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;3.自行轉讓名額者，如有任何意外發生參賽者應負連帶保險理賠及法律責任</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;4.如活動遇不可抗力之因素，如颱風、地震、水災、或因中央/地方行政單位公告無法舉辦/</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;建議取消「大型群聚活動」等因素，為顧及參與者的安全，大會有權以簡訊通知及官方</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;中華奧會粉專公告活動取消或順延；若造成活動取消，大會將於官方粉絲專頁公告且</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;全額退款，但不於事後寄送已製作之紀念品，敬請見諒</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;5.參與者於報名截止日之後申請取消報名即退費者，主辦單位得不退還報名費。但參與者</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;因下列事由未能參與活動者，得檢附證明文件經主辦單位認可，向主辦單位申請取消報名</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;及退費，主辦單位應將全額退還報名費：</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;🔺成年家屬陪同者兵役或點閱、教育召集</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;🔺配合中央/地方行政單位公告不得參與大型群聚活動者</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;🔺其他因不可抗力且無法歸責於報名者之重大事故，並經主辦單位審核認可。 &nbsp; &nbsp; &nbsp;</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;6.若有任何報名、退費問題請電洽 02-8768-1866#570王先生，並轉接至小小英雄運動會</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;活動專案小組；服務時間：週一至週五10：00 - 18：00</p><p>&nbsp;</p><p>本活動辦法若有未盡詳細事宜，主辦單位得隨時修訂並保留修改活動內容細節之權利，且不另行通知&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>',
        '台南市', '東區中華東路1段1號', '2024-4-6 00:00:00',
        '2024-10-21 12:00:00', '2024-10-21 06:00:00', '2024-10-21 12:00:00', 1,
        5, 1, 0,0),
       ('異國饗宴-道地西班牙深度料理課', 8, 7,
        '<p style="text-align:center"><span style="color:#663366"><strong><span style="font-size:24px">異國饗宴-道地西班牙深度料理課 </span></strong></span></p><p style="text-align:center"><span style="color:#663366"><strong><span style="font-size:24px">Spanish cuisine cooking class</span></strong></span></p><p style="text-align:center"><span style="color:#2c3e50"><strong>活動日期：</strong></span><br><strong>2024/04/16（二）15:30-19:30(額滿)</strong><br><strong>2024/04/21（日）15:30-19:30(最後1個名額)<br>2024/05/19（日）15:30-19:30(額滿)<br>2024/07/06（六）15:30-19:30<br>2024/10/19（六）15:30-19:30</strong><br><span style="color:#2c3e50"><strong>(單日工作坊，學習五道料理，一起分工實作學習，老師示範教學，含用餐時間一小時)<br>活動地點：久號空間(台北市中山區南京東路一段15號4樓)<br>老師：Jerry<br>對象：不限<br>人數：6人<br>價錢：2450元/人(含晚餐五道料理、講義、器材、食材)<br>主辦單位：久號｜永壽文教基金會</strong></span></p><p style="text-align:center"><span style="font-size:12px"><span style="color:#2c3e50"><strong>※完成報名後，請務必加入久號官方LINE帳號@ninenine，加入後請發送貼圖告知我們報名姓名，我們將於課程開始前發送課前通知。</strong></span></span></p><p style="text-align:center">&nbsp;</p><h2>活動介紹</h2><p>西班牙料理最大的特色就是「融合」，結合了不同國家的料理手法在其中，在過程中也能隱約品嘗到法國、中東、美洲等飲食風味。<br>我們將運用「感官」來體驗西班牙料理，不管是料理手法還是食材運用，都可以從過程中去感受西班牙國人的熱情、直爽，且不拘小節的性格。</p><p>體驗歐洲料理最大的不同就是，我們都是邊喝酒邊做菜，輕鬆愜意的方式烹飪料理，讓時間慢慢帶出食物細微層次的風味，不同於中式大火快炒瞬間激發食材的香氣，歐洲料理擅長運用時間孕育出食材的深度美味。</p><p>所以一開始課程會從示範如何自製西班牙國酒-桑格利亞，運用濃厚風味的丹魄紅酒搭配季節水果與氣泡水，清爽又不失去紅酒的迷人風味，微微氣泡口感刺激舌尖，觸發人們的口腹之慾，接下來就是動動手指創造極簡的美味囉！</p><p>此外，這次的課程我們將在一個晚上學會五道最地道的西班牙料理，從水果酒、前菜、主餐和甜點，讓大家學會做完整的西班牙料理，其中也有最受矚目的Paella，俗稱「大鍋飯」大家熟知的海鮮燉飯，同時也是西班牙國人家庭聚餐、節慶的必備家常菜。</p><p>「吃飯」是最能讓人們凝聚在一起的一件事，分享料理的喜悅從動手做開始，為自己和家人舉辦一場小盛宴吧！</p><p>讓我們一起「用料理為你的幸福加分」</p><p>&nbsp;</p><p>Menu</p><p>1.西班牙火腿番茄麵包<br>Pan con tomate<br>(歐式麵包、蒜頭、自然熟番茄、塞拉諾12個月熟成火腿、西班牙特級橄欖油）</p><p>2.經典馬鈴薯烘蛋<br>Tortilla de patatas<br>(紅皮洋芋、北海道洋蔥、西班牙橄欖油、牧場紅蛋）</p><p>3.海鮮鍋飯<br>Paella de marisco<br>(花蓮無毒女兒蝦、花枝、紐西蘭淡菜、蛤蜊、西班牙Bomba米、自然熟番茄、番紅花、西班牙煙燻紅椒粉)</p><p>4.加泰隆尼亞風味布蕾<br>Crema catalana<br>(北海道鮮乳、肉桂、日本三井製糖、黃檸檬、白蘭地、牧場紅蛋）</p><p>5.桑格利亞（西班牙水果酒）<br>(西班牙Tempranillo 丹魄紅酒、季節水果、100%柳橙汁、白蘭地、肉桂)</p><p><img alt="NinenineClass 0909 1111 Spanish cooking 04" src="https://www.ninenine.org/images/events/Class/2023/0909-1111-Spanish-cooking/NinenineClass-0909-1111-Spanish_cooking_04.jpg" style="height:1479px; width:1109px"></p><p><img alt="NinenineClass 0909 1111 Spanish cooking 05" src="https://www.ninenine.org/images/events/Class/2023/0909-1111-Spanish-cooking/NinenineClass-0909-1111-Spanish_cooking_05.jpg" style="height:1109px; width:1479px"></p><p><img alt="NinenineClass 0909 1111 Spanish cooking 06" src="https://www.ninenine.org/images/events/Class/2023/0909-1111-Spanish-cooking/NinenineClass-0909-1111-Spanish_cooking_06.jpg" style="height:1109px; width:1479px"></p><p><img alt="NinenineClass 0909 1111 Spanish cooking 07" src="https://www.ninenine.org/images/events/Class/2023/0909-1111-Spanish-cooking/NinenineClass-0909-1111-Spanish_cooking_07.jpg" style="height:1043px; width:1571px"></p><p><img alt="NinenineClass 0909 1111 Spanish cooking 08" src="https://www.ninenine.org/images/events/Class/2023/0909-1111-Spanish-cooking/NinenineClass-0909-1111-Spanish_cooking_08.jpg" style="height:1046px; width:1568px"></p><p><img src="https://static.accupass.com/eventintro/2401260629361273992429.jpg" style="height:2983px; width:4475px"></p><p><img src="https://static.accupass.com/eventintro/2401260629531056015013.jpg" style="height:3581px; width:5371px"></p><p><img src="https://static.accupass.com/eventintro/2401260630205043660690.jpg" style="height:2971px; width:4456px"></p><p><img src="https://static.accupass.com/eventintro/2401260630022997396930.jpg" style="height:3648px; width:5472px"></p><p><img alt="ninenine funds 01" src="https://www.ninenine.org/images/ninenine_funds_01.jpg" style="height:2000px; width:2000px"></p><h2>師資說明</h2><h3>Jerry</h3><p>Jerry幸福料理體驗室主理人</p><p>經歷：<br>曾赴西班牙學習當地料理<br>歐傑洛義式餐廳<br>OGGI 歐奇窯烤比薩<br>台北亞都麗緻飯店<br>北投麗禧溫泉酒店</p><p>秉信著<br>「料理是為了帶給人們幸福」<br>餐飲科系7年/職人經驗15年<br>擅長家常料理系列: 歐式、台式、部分日韓式、東南亞等料理</p><p>幸福料理體驗室在做什麼?<br>提供給喜愛料理、想學基礎烹飪的煮廚們有質感的學習環境。</p><p>體驗料理所帶來的不同感受，運用豐富的 感官經驗來組成「自己的獨特風味」。</p><p>學習不同的烹調技巧與添增風味的秘方， 不只學習食譜，更重要的是學習「如何料理」。</p>',
        '高雄市', '鹽埕區河西路1號', '2024-4-7 00:00:00', '2024-8-21 22:00:00',
        '2024-5-21 18:00:00', '2024-8-21 22:00:00', 1, 5, 1, 0,65),
       ('異國饗宴-道地西班牙深度料理課', 8, 7,
        '<p style="text-align:center"><span style="color:#663366"><strong><span style="font-size:24px">異國饗宴-道地西班牙深度料理課 </span></strong></span></p><p style="text-align:center"><span style="color:#663366"><strong><span style="font-size:24px">Spanish cuisine cooking class</span></strong></span></p><p style="text-align:center"><span style="color:#2c3e50"><strong>活動日期：</strong></span><br><strong>2024/04/16（二）15:30-19:30(額滿)</strong><br><strong>2024/04/21（日）15:30-19:30(最後1個名額)<br>2024/05/19（日）15:30-19:30(額滿)<br>2024/07/06（六）15:30-19:30<br>2024/10/19（六）15:30-19:30</strong><br><span style="color:#2c3e50"><strong>(單日工作坊，學習五道料理，一起分工實作學習，老師示範教學，含用餐時間一小時)<br>活動地點：久號空間(台北市中山區南京東路一段15號4樓)<br>老師：Jerry<br>對象：不限<br>人數：6人<br>價錢：2450元/人(含晚餐五道料理、講義、器材、食材)<br>主辦單位：久號｜永壽文教基金會</strong></span></p><p style="text-align:center"><span style="font-size:12px"><span style="color:#2c3e50"><strong>※完成報名後，請務必加入久號官方LINE帳號@ninenine，加入後請發送貼圖告知我們報名姓名，我們將於課程開始前發送課前通知。</strong></span></span></p><p style="text-align:center">&nbsp;</p><h2>活動介紹</h2><p>西班牙料理最大的特色就是「融合」，結合了不同國家的料理手法在其中，在過程中也能隱約品嘗到法國、中東、美洲等飲食風味。<br>我們將運用「感官」來體驗西班牙料理，不管是料理手法還是食材運用，都可以從過程中去感受西班牙國人的熱情、直爽，且不拘小節的性格。</p><p>體驗歐洲料理最大的不同就是，我們都是邊喝酒邊做菜，輕鬆愜意的方式烹飪料理，讓時間慢慢帶出食物細微層次的風味，不同於中式大火快炒瞬間激發食材的香氣，歐洲料理擅長運用時間孕育出食材的深度美味。</p><p>所以一開始課程會從示範如何自製西班牙國酒-桑格利亞，運用濃厚風味的丹魄紅酒搭配季節水果與氣泡水，清爽又不失去紅酒的迷人風味，微微氣泡口感刺激舌尖，觸發人們的口腹之慾，接下來就是動動手指創造極簡的美味囉！</p><p>此外，這次的課程我們將在一個晚上學會五道最地道的西班牙料理，從水果酒、前菜、主餐和甜點，讓大家學會做完整的西班牙料理，其中也有最受矚目的Paella，俗稱「大鍋飯」大家熟知的海鮮燉飯，同時也是西班牙國人家庭聚餐、節慶的必備家常菜。</p><p>「吃飯」是最能讓人們凝聚在一起的一件事，分享料理的喜悅從動手做開始，為自己和家人舉辦一場小盛宴吧！</p><p>讓我們一起「用料理為你的幸福加分」</p><p>&nbsp;</p><p>Menu</p><p>1.西班牙火腿番茄麵包<br>Pan con tomate<br>(歐式麵包、蒜頭、自然熟番茄、塞拉諾12個月熟成火腿、西班牙特級橄欖油）</p><p>2.經典馬鈴薯烘蛋<br>Tortilla de patatas<br>(紅皮洋芋、北海道洋蔥、西班牙橄欖油、牧場紅蛋）</p><p>3.海鮮鍋飯<br>Paella de marisco<br>(花蓮無毒女兒蝦、花枝、紐西蘭淡菜、蛤蜊、西班牙Bomba米、自然熟番茄、番紅花、西班牙煙燻紅椒粉)</p><p>4.加泰隆尼亞風味布蕾<br>Crema catalana<br>(北海道鮮乳、肉桂、日本三井製糖、黃檸檬、白蘭地、牧場紅蛋）</p><p>5.桑格利亞（西班牙水果酒）<br>(西班牙Tempranillo 丹魄紅酒、季節水果、100%柳橙汁、白蘭地、肉桂)</p><p><img alt="NinenineClass 0909 1111 Spanish cooking 04" src="https://www.ninenine.org/images/events/Class/2023/0909-1111-Spanish-cooking/NinenineClass-0909-1111-Spanish_cooking_04.jpg" style="height:1479px; width:1109px"></p><p><img alt="NinenineClass 0909 1111 Spanish cooking 05" src="https://www.ninenine.org/images/events/Class/2023/0909-1111-Spanish-cooking/NinenineClass-0909-1111-Spanish_cooking_05.jpg" style="height:1109px; width:1479px"></p><p><img alt="NinenineClass 0909 1111 Spanish cooking 06" src="https://www.ninenine.org/images/events/Class/2023/0909-1111-Spanish-cooking/NinenineClass-0909-1111-Spanish_cooking_06.jpg" style="height:1109px; width:1479px"></p><p><img alt="NinenineClass 0909 1111 Spanish cooking 07" src="https://www.ninenine.org/images/events/Class/2023/0909-1111-Spanish-cooking/NinenineClass-0909-1111-Spanish_cooking_07.jpg" style="height:1043px; width:1571px"></p><p><img alt="NinenineClass 0909 1111 Spanish cooking 08" src="https://www.ninenine.org/images/events/Class/2023/0909-1111-Spanish-cooking/NinenineClass-0909-1111-Spanish_cooking_08.jpg" style="height:1046px; width:1568px"></p><p><img src="https://static.accupass.com/eventintro/2401260629361273992429.jpg" style="height:2983px; width:4475px"></p><p><img src="https://static.accupass.com/eventintro/2401260629531056015013.jpg" style="height:3581px; width:5371px"></p><p><img src="https://static.accupass.com/eventintro/2401260630205043660690.jpg" style="height:2971px; width:4456px"></p><p><img src="https://static.accupass.com/eventintro/2401260630022997396930.jpg" style="height:3648px; width:5472px"></p><p><img alt="ninenine funds 01" src="https://www.ninenine.org/images/ninenine_funds_01.jpg" style="height:2000px; width:2000px"></p><h2>師資說明</h2><h3>Jerry</h3><p>Jerry幸福料理體驗室主理人</p><p>經歷：<br>曾赴西班牙學習當地料理<br>歐傑洛義式餐廳<br>OGGI 歐奇窯烤比薩<br>台北亞都麗緻飯店<br>北投麗禧溫泉酒店</p><p>秉信著<br>「料理是為了帶給人們幸福」<br>餐飲科系7年/職人經驗15年<br>擅長家常料理系列: 歐式、台式、部分日韓式、東南亞等料理</p><p>幸福料理體驗室在做什麼?<br>提供給喜愛料理、想學基礎烹飪的煮廚們有質感的學習環境。</p><p>體驗料理所帶來的不同感受，運用豐富的 感官經驗來組成「自己的獨特風味」。</p><p>學習不同的烹調技巧與添增風味的秘方， 不只學習食譜，更重要的是學習「如何料理」。</p>',
        '高雄市', '鹽埕區河西路1號', '2024-4-7 00:00:00', '2024-8-21 22:00:00',
        '2024-5-21 18:00:00', '2024-8-21 22:00:00', 1, 5, 1, 0,0);


create table event_coupon
(
    event_coupon_no       int auto_increment
        primary key,
    host_no               int         not null,
    event_coupon_name     varchar(100) null,
    coupon_code           varchar(10) not null,
    usage_limit           int         not null,
    remaining_times       int null,
    min_spend             int null,
    event_coupon_discount int         not null,
    start_time            datetime null,
    end_time              datetime null,
    event_coupon_status   tinyint     not null,
    coupon_desc           varchar(150) null,
    constraint event_coupon_uk
        unique (coupon_code),
    constraint event_coupon_host_host_no_fk
        foreign key (host_no) references host (host_no)
);

insert into event_coupon (host_no, event_coupon_name, coupon_code, usage_limit, remaining_times, min_spend,
                          event_coupon_discount, start_time, end_time, event_coupon_status, coupon_desc)
values (1, '城市馬拉松優惠', 'RUN2024', 500, 500, 0, 10, '2024-04-17 00:00:00', '2024-04-18 23:59:59', 1,
        '參加城市馬拉松可享受九折優惠'),
       (2, '古典音樂夜優惠', 'MUSIC2024', 300, 300, 100, 15, '2024-04-22 00:00:00', '2024-04-23 23:59:59', 1,
        '購買音樂會門票滿100元享八五折'),
       (3, '市場營銷高峰會折扣', 'MKT2024', 200, 200, 200, 20, '2024-04-29 00:00:00', '2024-04-30 23:59:59', 1,
        '研討會報名滿200元可享八折'),
       (4, '現代藝術展門票', 'ART2024', 250, 250, 150, 10, '2024-05-05 00:00:00', '2024-05-06 23:59:59', 1,
        '購票滿150元享九折'),
       (5, '全國游泳錦標賽優惠', 'SWIM2024', 400, 400, 0, 5, '2024-05-11 00:00:00', '2024-05-12 23:59:59', 1,
        '報名費直接享九五折'),
       (6, '莎士比亞戲劇節', 'DRAMA2024', 350, 350, 0, 20, '2024-05-17 00:00:00', '2024-05-18 23:59:59', 1,
        '所有戲票八折優惠'),
       (7, '國際電影節門票優惠', 'FILM2024', 500, 500, 100, 25, '2024-05-23 00:00:00', '2024-05-24 23:59:59', 1,
        '電影票滿100元享七五折'),
       (8, '法式烹飪工作坊折扣', 'COOK2024', 150, 150, 300, 15, '2024-05-29 00:00:00', '2024-05-30 23:59:59', 1,
        '烹飪課程滿300元享八五折'),
       (9, '自然攝影工作坊優惠', 'PHOTO2024', 100, 100, 250, 20, '2024-06-04 00:00:00', '2024-06-05 23:59:59', 1,
        '攝影課程滿250元可享八折'),
       (10, '水彩畫課程優惠', 'PAINT2024', 200, 200, 150, 10, '2024-06-09 00:00:00', '2024-06-10 23:59:59', 1,
        '畫課滿150元享九折');


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
    reset_token				 varchar(200) null,
    verification_code		 varchar(200) null,
    verification_code_expiry	 datetime null,
    constraint member_uk1
        unique (member_mail),
    constraint member_uk2
        unique (member_phone)
);

INSERT INTO member (member_mail, member_pwd, member_name, birthday, gender, member_points, member_phone, address, common_recipient, common_recipient_phone, common_recipient_address, member_status)
VALUES ('sarahchang456@gmail.com', 'a12345', '張雨琪', '2000-01-01', 1, 100, '0912345678', '台北市大安區復興南路一段100號','張雨琪','0912345678','台北市大安區復興南路一段100號', 1),
       ('johnwang123@gmail.com', 'b123456', '王宇軒', '2001-02-01', 0, 100, '0928765432', '新北市板橋區文化路二段50號','王宇軒','0928765432','新北市板橋區文化路二段50號', 1),
       ('emilylee999@gmail.com', 'c1234567', '李思婷', '2002-03-01', 1, 100, '0934567890', '桃園市桃園區中正路300號','李思婷','0934567890','桃園市桃園區中正路300號', 1),
       ('davidchen123@gmail.com', 'd12345678', '陳俊宇', '2003-04-01', 0, 100, '0987654321', '台中市西屯區文心路四段150號','陳俊宇', '0987654321','台中市西屯區文心路四段150號', 1),
       ('lindalin888@gmail.com', 'e112233445', '林雅琪', '2004-05-01', 1, 100, '0956123456', '台南市中西區民權路二段200號', '林雅琪','0956123456','台南市中西區民權路二段200號', 1),
       ('kevinhwang333@gmail.com', 'f654321', '黃文彬', '2000-06-01', 0, 100, '0978654321', '高雄市前鎮區中山四路100號','黃文彬','0978654321','高雄市前鎮區中山四路100號', 1),
       ('jennywu111@gmail.com', 'g102938', '吳雅琴', '2001-07-01', 1, 100, '0903210987', '宜蘭縣宜蘭市舊城路一段80號','吳雅琴','0903210987','宜蘭縣宜蘭市舊城路一段80號', 1),
       ('christsai@gmail.com', 'h11111111', '蔡宗翰', '2002-08-01', 0, 100, '0965432109', '新竹縣竹北市中華路100號','蔡宗翰','0965432109','新竹縣竹北市中華路100號', 1),
       ('amyhsu222@gmail.com', 'i11112222', '許心如', '2001-09-01', 1, 100, '0921098765', '苗栗縣苗栗市中正路二段50號','許心如','0921098765','苗栗縣苗栗市中正路二段50號', 1),
       ('jennycheng111@gmail.com', 'k12341234', '鄭子婷', '2002-10-01', 0, 100, '0943210987', '嘉義縣嘉義市民生北路三段150號','鄭子婷','0943210987', '嘉義縣嘉義市民生北路三段150號', 1),
       ('ezbantest+11@gmail.com', 'ezbantest', '王大名', '2002-10-01', 0, 100, '0900000000', '嘉義縣嘉義市民生北路三段150號','鄭子婷','0943210987', '嘉義縣嘉義市民生北路三段150號', 1);

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

INSERT INTO `ezban`.`birthday_coupon_holder` (`member_no`, `birthday_coupon_no`, `coupon_usage_status`, `validity_date`)
VALUES ('1', '1', '0', DATE_ADD(CURDATE(), INTERVAL 1 MONTH)),
       ('2', '2', '0', DATE_ADD(CURDATE(), INTERVAL 1 MONTH)),
       ('3', '3', '1', DATE_ADD(CURDATE(), INTERVAL 1 MONTH)),
       ('4', '4', '1', DATE_ADD(CURDATE(), INTERVAL 1 MONTH)),
       ('5', '5', '0', DATE_ADD(CURDATE(), INTERVAL 1 MONTH));


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
VALUES (1, 1, 5, '這個活動太棒了！氣氛熱烈，節目精彩，讓人流連忘返！', '2024-04-18 13:00:10', 1),
       (1, 2, 5, '感謝主辦方舉辦這樣有意義的活動，我度過了一個愉快的時光！', '2024-04-10 13:00:19', 1),
       (1, 3, 5, '這個活動的組織安排很到位，讓參與者感到非常舒適和愉快！', '2024-04-13 14:01:12', 1),
       (1, 5, 5, '參加這個活動，收獲滿滿！學到了很多知識，收獲了新的朋友！', '2024-04-17 13:05:16', 1),
       (1, 6, 5, '活動內容豐富多彩，很有意思，讓人印象深刻！', '2024-04-18 11:00:15', 1),
       (1, 4, 5, '感謝主辦方為我們提供了這樣一個互動交流的平台，真的很有意義！', '2024-04-18 10:30:12', 1),
       (1, 7, 5, '活動的策劃和執行都很棒，讓人參與的欲望更強烈！', '2024-04-15 09:04:12', 1),
       (1, 9, 5, '參加這個活動，收獲了新的見解，也結交了一些志同道合的朋友！', '2024-04-15 19:00:30', 1),
       (1, 8, 5, '活動的主題很獨特，內容很豐富，值得推薦給更多的朋友！', '2024-04-16 16:00:00', 1),
       (1, 10, 5, '感謝主辦方用心舉辦這個活動，讓我們度過了一個愉快的時光！', '2024-04-09 18:30:15', 1);


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
VALUES (1, 1, 1, '系統通知：您的帳戶密碼已成功修改，請妥善保管您的新密碼。', 0, NOW()),
       (2, 2, 1, '系統提醒：您的訂單已成功支付，請耐心等待發貨。', 0, NOW()),
       (10, 1, 3, '系統提示：您有一封新的私信消息，請及時查看。', 0, NOW()),
       (4, 3, 1, '系統通知：您的評論已收到點讚，感謝您的支持與參與。', 0, NOW()),
       (1, 1, 2, '系統消息：您的預訂活動已取消，請查看詳情並聯系客服進行退款。', 0, NOW()),
       (5, 2, 1, '系統提醒：您的包裹已發出，請留意物流信息並準備簽收。', 0, NOW()),
       (7, 1, 2, '系統通知：您的評論已被檢舉，請等待管理員審核結果。', 0, NOW()),
       (7, 1, 3, '系統通知：您報名的活動已被取消，請查看詳情並聯系客服進行退款。', 0, NOW()),
       (2, 3, 2, '系統通知：您的賬戶已成功注冊，請查看您的電子郵箱以完成驗證。', 0, NOW()),
       (8, 1, 1, '系統消息：已收到您的檢舉，感謝您的反饋，我們會盡快處理您的請求。', 0, NOW());


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
VALUES (1, 10, '2024-04-11 13:05:15'),
       (1, 15, '2024-04-17 16:10:10'),
       (4, 10, '2024-04-18 10:00:00'),
       (2, 10, '2024-04-10 18:30:30'),
       (1, 20, '2024-04-11 13:00:00'),
       (5, 10, '2024-04-18 09:10:10'),
       (1, 15, '2024-04-09 18:00:00'),
       (9, 10, '2024-04-18 13:00:10'),
       (2, 10, '2024-04-17 13:30:15'),
       (2, 30, '2024-04-11 12:30:30');


create table qa
(
    qa_no      int auto_increment
        primary key,
    qa_title   varchar(50) null,
    qa_content longtext    null
);

INSERT INTO qa (qa_title, qa_content)
VALUES ('忘記加入會員時設定的密碼', '點擊官網右側上端的Login,在登入頁面的【找回密碼】中可以變更密碼。
填入會員註冊時填寫的姓名、生日以及帳號信箱，就會發送安全驗證碼到信箱 (需與註冊時提供的帳號信箱、姓名、生日一致)。
最後，輸入從Email收到的安全驗證碼後就可以重新設定密碼。'),

       ('為什麼我沒有收到註冊驗證信?', '當您完成註冊步驟並將資料填妥送出後，系統約3-5分鐘內會發出認證信至您的電子信箱。若仍未收到認證信，建議您可先至垃圾郵件、社交網路、促銷內容中確認或使用重新發送驗證信功能。
提醒您：驗證網址的有效時間為發信後的一個小時內。'),
       ('請問繳款後的周邊商品何時寄送', '活動物資通常最晚將於活動開始前一週開始陸續寄送，若活動開始前尚未收到物資，請與主辦單位聯繫。'),
       ('有哪些付款方式可以選擇', '請參閱各活動簡章之繳費方式，依主辦單位設定而有所不同'),
       ('我要申請退貨，該如何處理', '為了提供用戶更優質的購物環境與消費者體驗，所有賣家皆須依照消保法提供鑑賞期服務，更詳細的情形請查詢消保法');



create table save_event
(
    save_event_no int not null auto_increment primary key,
    member_no     int not null,
    event_no      int not null,
    save_status     tinyint not null,
    foreign key (event_no) references event (event_no),
    foreign key (member_no) references member (member_no)
);

INSERT INTO save_event (member_no, event_no, save_status)
VALUES (1, 1, 1),
       (2, 2, 0),
       (3, 3, 1),
       (4, 4, 0),
       (5, 5, 1),
       (6, 6, 0),
       (7, 7, 1),
       (8, 8, 0),
       (9, 9, 1),
       (10, 10, 0);


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
    ticket_order_status            tinyint  null,
    ticket_order_allocation_amount int      null,
    ticket_order_allocation_status tinyint  null,
    constraint ticket_order_event_coupon_event_coupon_no_fk
        foreign key (event_coupon_no) references event_coupon (event_coupon_no),
    constraint ticket_order_member_member_no_fk
        foreign key (member_no) references member (member_no)
);

INSERT INTO ticket_order (member_no, event_coupon_no, ticket_order_amount, event_coupon_discount,
                          ticket_checkout_amount, ticket_order_time, ticket_order_pay_time, ticket_order_payment_status, ticket_order_status)
VALUES (1, 1, 635, 10, 625, NOW(), NOW() + INTERVAL 30 MINUTE, 3, 1),
       (1, 1, 285, 10, 275, NOW(), NOW() + INTERVAL 30 MINUTE, 3, 1),
       (2, 1, 285, 10, 275, NOW(), NOW() + INTERVAL 30 MINUTE, 3, 1),
       (2, 1, 285, 10, 275, NOW(), NOW() + INTERVAL 30 MINUTE, 3, 1),
       (3, 1, 285, 10, 275, NOW(), NOW() + INTERVAL 30 MINUTE, 3, 1),
       (3, 1, 285, 10, 275, NOW(), NOW() + INTERVAL 30 MINUTE, 3, 1),
       (4, 1, 285, 10, 275, NOW(), NOW() + INTERVAL 30 MINUTE, 3, 1),
       (4, 1, 285, 10, 275, NOW(), NOW() + INTERVAL 30 MINUTE, 3, 1),
       (5, 1, 285, 10, 275, NOW(), NOW() + INTERVAL 30 MINUTE, 3, 1),
       (5, 1, 285, 10, 275, NOW(), NOW() + INTERVAL 30 MINUTE, 3, 1);


create table ticket_type
(
    ticket_type_no          int auto_increment
        primary key,
    event_no                int           not null,
    ticket_type_name        varchar(50)   not null,
    included_tickets        int default 1 not null,
    purchase_start_time     datetime      not null,
    purchase_end_time       datetime      not null,
    ticket_type_info        varchar(200)  null,
    ticket_type_price       int           not null,
    ticket_type_qty         int           not null,
    remaining_ticket_qty    int           null,
    purchase_quantity_limit int           not null,
    constraint ticket_type_fk
        foreign key (event_no) references event (event_no)
);

INSERT INTO ticket_type (event_no, ticket_type_name, included_tickets, purchase_start_time, purchase_end_time,
                         ticket_type_price, ticket_type_qty, remaining_ticket_qty, Purchase_quantity_limit)
VALUES (1, '單人票', 1, NOW(), '2024-6-30 00:00:00', 100, 10, 9, 5),
       (1, '雙人票', 2, NOW(), '2024-6-30 00:00:00', 185, 10, 9, 5),
       (1, '四人套票', 4, NOW(), '2024-6-30 00:00:00', 350, 1, 0, 1),
       (2, '單人票', 1, NOW(), '2024-6-30 00:00:00', 100, 10, 9, 5),
       (2, '雙人票', 2, NOW(), '2024-6-30 00:00:00', 185, 10, 9, 5),
       (2, '四人套票', 4, NOW(), '2024-6-30 00:00:00', 350, 10, 10, 5),
       (3, '單人票', 1, NOW(), '2024-6-30 00:00:00', 100, 10, 9, 5),
       (3, '雙人票', 2, NOW(), '2024-6-30 00:00:00', 185, 10, 9, 5),
       (3, '四人套票', 4, NOW(), '2024-6-30 00:00:00', 350, 10, 10, 5),
       (4, '單人票', 1, NOW(), '2024-6-30 00:00:00', 100, 10, 9, 5),
       (4, '雙人票', 2, NOW(), '2024-6-30 00:00:00', 185, 10, 9, 5),
       (4, '四人套票', 4, NOW(), '2024-6-30 00:00:00', 350, 10, 10, 5),
       (5, '單人票', 1, NOW(), '2024-6-30 00:00:00', 100, 10, 9, 5),
       (5, '雙人票', 2, NOW(), '2024-6-30 00:00:00', 185, 10, 9, 5),
       (5, '四人套票', 4, NOW(), '2024-6-30 00:00:00', 350, 10, 10, 5),
       (6, '單人票', 1, NOW(), '2024-6-30 00:00:00', 100, 10, 9, 5),
       (6, '雙人票', 2, NOW(), '2024-6-30 00:00:00', 185, 10, 9, 5),
       (6, '四人套票', 4, NOW(), '2024-6-30 00:00:00', 350, 10, 10, 5),
       (7, '單人票', 1, NOW(), '2024-6-30 00:00:00', 100, 10, 9, 5),
       (7, '雙人票', 2, NOW(), '2024-6-30 00:00:00', 185, 10, 9, 5),
       (7, '四人套票', 4, NOW(), '2024-6-30 00:00:00', 350, 10, 10, 5),
       (8, '單人票', 1, NOW(), '2024-6-30 00:00:00', 100, 10, 9, 5),
       (8, '雙人票', 2, NOW(), '2024-6-30 00:00:00', 185, 10, 9, 5),
       (8, '四人套票', 4, NOW(), '2024-6-30 00:00:00', 350, 10, 10, 5),
       (9, '單人票', 1, NOW(), '2024-6-30 00:00:00', 100, 10, 9, 5),
       (9, '雙人票', 2, NOW(), '2024-6-30 00:00:00', 185, 10, 9, 5),
       (9, '四人套票', 4, NOW(), '2024-6-30 00:00:00', 350, 10, 10, 5),
       (10, '單人票', 1, NOW(), '2024-6-30 00:00:00', 100, 10, 9, 5),
       (10, '雙人票', 2, NOW(), '2024-6-30 00:00:00', 185, 10, 9, 5),
       (10, '四人套票', 4, NOW(), '2024-6-30 00:00:00', 350, 10, 10, 5),
       (11, '單人票', 1, NOW(), '2024-6-30 00:00:00', 100, 10, 9, 5),
       (11, '雙人票', 2, NOW(), '2024-6-30 00:00:00', 185, 10, 9, 5),
       (11, '四人套票', 4, NOW(), '2024-6-30 00:00:00', 350, 10, 10, 5),
       (12, '單人票', 1, NOW(), '2024-6-30 00:00:00', 100, 10, 9, 5),
       (12, '雙人票', 2, NOW(), '2024-6-30 00:00:00', 185, 10, 9, 5),
       (12, '四人套票', 4, NOW(), '2024-6-30 00:00:00', 350, 10, 10, 5);



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
VALUES (1, 1, 100, 1, 1),
       (2, 1, 185, 1, 2),
       (3, 1, 350, 1, 4),
       (4, 2, 100, 1, 1),
       (5, 2, 185, 1, 2),
       (7, 3, 100, 1, 1),
       (8, 3, 185, 1, 2),
       (10, 4, 100, 1, 1),
       (11, 4, 185, 1, 2),
       (13, 5, 100, 1, 1),
       (14, 5, 185, 1, 2),
       (16, 6, 100, 1, 1),
       (17, 6, 185, 1, 2),
       (19, 7, 100, 1, 1),
       (20, 7, 185, 1, 2),
       (22, 8, 100, 1, 1),
       (23, 8, 185, 1, 2),
       (25, 9, 100, 1, 1),
       (26, 9, 185, 1, 2),
       (28, 10, 100, 1, 1),
       (29, 10, 185, 1, 2);


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
) auto_increment = 3120001;

INSERT INTO qrcode_ticket (ticket_order_detail_no, member_no, ticket_usage_status, ticket_valid_time)
VALUES
    (1, 1, 0, NOW()),
    (2, 2, 1, NOW()),
    (3, 3, 2, NOW()),
    (4, 4, 0, NOW()),
    (5, 5, 1, NOW()),
    (6, 6, 2, NOW()),
    (7, 7, 0, NOW()),
    (8, 8, 0, NOW()),
    (9, 9, 2, NOW()),
    (10, 10, 0, NOW());


CREATE TABLE product_category
(
    product_category_no   INT NOT NULL AUTO_INCREMENT,
    product_category_name VARCHAR(50),
    CONSTRAINT product_category_no_pk PRIMARY KEY (product_category_no)
);

INSERT INTO product_category (product_category_name)
VALUES
    ('服飾'),
    ('配件'),
    ('廚房用品'),
    ('生活用品'),
    ('書籍'),
    ('文化藝術'),
    ('戶外用品');

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
    product_add_time     datetime    null,
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

INSERT INTO product (product_category_no, product_name, host_no, product_desc, product_price, product_add_qty, remaining_qty, product_add_time, product_remove_time, product_status, product_total_rating, product_rating_count)
VALUES
    (1, '台北屋頂電影院限定版T恤', 1, '這款限定版T恤採用優質棉料製成，正面印有獨特的台北屋頂電影院logo和台北市夜景圖案，適合日常穿著或作為紀念品。', 250, 150, 3, '2024-04-01 00:00:00', '2024-06-10 00:00:00', 1, 0, 0),
    (6, '屋頂電影院紀念音樂盒', 1, '手工製作的音樂盒，每當打開時播放經典電影主題曲。外觀小巧精緻，內藏手繪的台北屋頂電影院場景，是電影愛好者的收藏佳品。', 350, 100, 2, '2024-04-01 00:00:00', '2024-06-10 00:00:00', 1, 0, 0),
    (1, '府城超馬限定版運動T恤', 2, '使用透氣材料製成的運動T恤，印有府城超級馬拉松的特別版圖案和2024年的活動標誌，適合馬拉松期間穿著。', 300, 200, 1, '2024-04-02 00:00:00', '2024-09-30 00:00:00', 1, 0, 0),
    (4, '府城超馬紀念水壺', 2, '高品質不銹鋼製水壺，表面印有府城超級馬拉松的標誌，是跑者和觀眾的理想飲水選擇。', 850, 250, 5, '2024-04-02 00:00:00', '2024-09-30 00:00:00', 1, 0, 0),
    (2, '府城超馬精神手環', 2, '色彩鮮明的硅膠手環，刻有“千馬精神憾動府城”口號，既時尚又具紀念意義。', 150, 500, 4, '2024-04-02 00:00:00','2024-09-30 00:00:00', 1, 0, 0),
    (7, '府城超馬夜跑頭燈', 2, '專為夜跑設計的頭燈，亮度適中，確保夜間安全，印有府城超級馬拉松的標誌，適合長時間佩戴。', 220, 150, 150, '2024-04-02 00:00:00', '2024-09-30 00:00:00', 1, 0, 0),
    (1, '清紫野餐派對紀念帽', 3, '專為校慶活動設計的野餐帽，上面繡有清華校慶的標誌和2024年活動特色圖案，適合戶外活動使用。', 200, 50, 50, '2024-04-03 00:00:00', '2024-05-27 00:00:00', 1, 0, 0),
    (7, '清紫野餐派對折疊野餐墊', 3, '便攜式折疊野餐墊，印有清華大學標誌和校慶圖案，專為野餐和戶外活動設計，易於攜帶且清洗方便。', 120, 100, 100, '2024-04-03 00:00:00', '2024-05-27 00:00:00', 1, 0, 0),
    (4, '清華校慶限定環保購物袋', 3, '環保購物袋，製作精良，以清華大學校徽和校慶色彩為設計主題，適合日常使用或作為紀念品。', 100, 90, 80, '2024-04-03 00:00:00', '2024-05-27 00:00:00', 1, 0, 0),
    (6, '台北新藝術博覽會紀念畫冊', 5, '精裝畫冊，收錄2024年博覽會的精選作品和藝術家介紹。畫冊封面藝術化設計，包含藝術總監李善單的導言和展覽精華，是藝術收藏者必備。', 450, 100, 90, '2024-04-05 00:00:00', '2024-05-30 00:00:00', 1, 0, 0),
    (6, '藝術博覽會限量版素描本', 5, '高質感的素描本，封面由參展藝術家設計，內頁含有空白和印有淡淡藝術作品輪廓的頁面，適合用於現場速寫或日常創作。', 350, 200, 200, '2024-04-05 00:00:00', '2024-05-30 00:00:00', 1, 0, 0),
    (6, '藝術博覽會專屬書簽組', 5, '一組四款，每款書簽均由不同藝術家設計，展示其代表作的細節。材質為厚紙質和金屬合成，附有精美繩絲，是閱讀愛好者的理想選擇。', 120, 400, 400, '2024-04-05 00:00:00', '2024-05-30 00:00:00', 1, 0, 0),
    (1, '2024小小英雄運動會紀念衣', 6, '高質感紀念衣，印有活動標誌和日期，適合活動當天穿著，留作紀念。', 250, 100, 100, '2024-04-06 00:00:00', '2024-05-30 00:00:00', 1, 0, 0),
    (2, '2024小小英雄運動會證件帶', 6, '紀念證件帶，供參與者活動當天佩戴，具有身份識別和紀念價值。', 350, 100, 100, '2024-04-06 00:00:00', '2024-05-30 00:00:00', 1, 0, 0),
    (4, '2024小小英雄運動會紀念束口袋', 6, '實用的紀念束口袋，上印活動標誌，方便參與者存放個人物品，並作為活動紀念。', 400, 150, 150, '2024-04-06 00:00:00', '2024-05-30 00:00:00', 1, 0, 0),
    (3, '西班牙風味調味料組', 7, '精選西班牙進口香料和調味料，包括番紅花、煙燻紅椒粉等，完美複製西班牙菜餚的經典風味。', 450, 100, 100, '2024-04-07 00:00:00', '2024-05-30 00:00:00', 1, 0, 0),
    (3, '定製烹飪圍裙', 7, '高質量的廚房圍裙，帶有印有西班牙料理課程logo的定製設計，適合烹飪時穿戴。', 200, 150, 150, '2024-04-07 00:00:00', '2024-05-30 00:00:00', 1, 0, 0),
    (5, '西班牙食譜', 7, '精美印製的食譜書，包括各種傳統西班牙菜餚的詳細做法和故事，適合熱愛西班牙文化和烹飪的美食愛好者。', 300, 20, 20, '2024-04-07 00:00:00', '2024-05-30 00:00:00', 1, 0, 0),
    (3, '西班牙風情手工陶瓷碟', 7, '這套西班牙風情手工陶瓷碟，精美而獨特的設計，讓您的西班牙料理更顯風味。', 350, 30, 30, '2024-04-07 00:00:00', '2024-05-30 00:00:00', 1, 0, 0);

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

INSERT INTO product_comment (product_no, member_no, product_rate, product_comment_content,
                             product_comment_date, product_comment_status)
VALUES (1, 1, 5, '這個產品真的很棒！質量非常好，性價比超高！強烈推薦！', '2024-04-18 13:00:10', 1),
       (2, 2, 5, '很喜歡這個商品，外觀時尚，功能也很實用，非常滿意！', '2024-04-10 13:00:19', 1),
       (3, 3, 5, '包裝很精美，商品完好無損，物流也很快，非常滿意購物體驗！', '2024-04-13 14:01:12', 1),
       (2, 5, 5, '商品收到了，質量不錯，顏色也很喜歡，與描述相符，非常滿意！', '2024-04-17 13:05:16', 1),
       (5, 6, 5, '售後服務態度很好，對我的問題耐心解答，讓我感到很放心！', '2024-04-18 11:00:15', 1),
       (1, 4, 4, '產品質量一般與描述稍有差異，但客服解決問題很及時，還算滿意！', '2024-04-18 10:30:12', 1),
       (4, 7, 4, '商品外觀漂亮，但使用起來有些不太方便，希望能改進一下設計！', '2024-04-15 09:04:12', 1),
       (4, 9, 5, '物流速度很快，商品質量也不錯，很滿意這次的購物體驗！', '2024-04-15 19:00:30', 1),
       (1, 8, 5, '這個商品值得購買，非常實用，我很喜歡！希望能有更多的顏色選擇！', '2024-04-16 16:00:00', 1);

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
    product_report_no int auto_increment primary key,
    product_no        int          not null,
    member_no         int          not null,
    admin_no          int,
    report_reason     varchar(100) not null,
    report_date       datetime     not null,
    report_status     tinyint default 0 not null ,
    constraint product_report_admin_admin_no_fk
        foreign key (admin_no) references admin (admin_no),
    constraint product_report_member_member_no_fk
        foreign key (member_no) references member (member_no),
    constraint product_report_product_product_no_fk
        foreign key (product_no) references product (product_no)
);

insert into product_report (product_no, member_no, admin_no, report_reason, report_date, report_status)
values (1,  1, null, '商品描述中含有不當字眼' , 	 now(), 0),
       (10, 6, 2, '價格太貴' ,          	     now(), 1),
       (5,  2, 2, '商品名稱用字不當' ,     	 now(), 2),
       (7,  3, 2, '不實用' ,             	 now(), 1),
       (5,  3, null, '檢舉是不需要理由的' , 		 now(), 0),
       (18, 2, null, '商品描述不清楚' ,     		 now(), 0),
       (11, 4, 2, '商品照片太模糊' ,        	 now(), 2),
       (15, 5, 2, '商品數量太少根本搶不到' ,  	 now(), 1),
       (10, 8, null, '商品照片放太少,不夠我參考' ,  now(), 0),
       (5,  7, null, '價格太便宜懷疑材質有問題' ,   now(), 0);


create table product_order
(
    product_order_no                int primary key auto_increment not null,
    member_no                       int          not null,
    product_price                   int          not null,
    product_coupon_discount         int      default 0,
    product_checkout_amount         int          not null,
    member_points                   int      default 0,
    birthday_coupon_no              int      default null,
    recipient                       varchar(50)  not null,
    recipient_phone                 varchar(15)  not null,
    recipient_address               varchar(200) not null,
    product_orderdate               dateTime default now(),
    product_paydate                 dateTime default now(),
    order_closedate                 dateTime default null,
    product_payment_status          tinyint  default 0  not null,
    product_process_status          tinyint  default 0  not null,
    product_order_allocation_amount int          not null,
    product_order_allocation_status tinyint  default 0  not null,
    foreign key (member_no) references member (member_no),
    foreign key (birthday_coupon_no) references birthday_coupon (birthday_coupon_no)
) auto_increment = 1001;

insert into product_order (member_no, product_price, product_coupon_discount, product_checkout_amount, member_points,
                           birthday_coupon_no, recipient, recipient_phone, recipient_address, product_orderdate,
                           product_paydate, order_closedate, product_payment_status, product_process_status,
                           product_order_allocation_amount, product_order_allocation_status)
values (1, 1250, 0,  1250, 0,   null, '張雨琪', '0912345678', '台北市大安區復興南路一段100號',  now(), now(),  null, 1,  1,  0, 0),
       (3, 150,  0,  50,   100, null, '李思婷', '0934567890', '桃園市桃園區中正路300號',       now(), now(),  now(), 0,  4,  150,  1),
       (9, 250,  0,  100,  100, null, '許心如', '0921098765', '苗栗縣苗栗市中正路二段50號',    now(), now(),  null,  0,  0,  250,  0),
       (7, 250,  50, 200,  0,   1, '吳雅琴', '0903210987', '宜蘭縣宜蘭市舊城路一段80號',    now(), now(),  now(),  0,  4,  250,  1),
       (2, 500,  50, 450,  0,   1, '王宇軒', '0928765432', '新北市板橋區文化路二段50號',    now(), now(),  null,  0,  0,  500,  0),
       (5, 250,  0,  250,  0,   null, '林雅琪', '0956123456', '台南市中西區民權路二段200號',   now(), now(),  null,  1,  1,  0,  0),
       (7, 790,  50, 740,  0,   1, '吳雅琴', '0903210987', '宜蘭縣宜蘭市舊城路一段80號',    now(), now(),  null,  0,  0,  790,  0),
       (3, 400,  0,  300,  100, null, '李思婷', '0934567890', '桃園市桃園區中正路300號',       now(), now(),  null,  1, 3,  0,  0),
       (2, 950,  0,  950,  0,   null, '王宇軒', '0928765432', '新北市板橋區文化路二段50號',    now(), now(),  null,  0,  2,  950,  0),
       (8, 250,  50, 200,  0,   1, '蔡宗翰', '0965432109', '新竹縣竹北市中華路100號',       now(), now(),  null,  0, 0,  250,  0);


create table product_order_detail
(
    product_order_detail_no int primary key auto_increment not null,
    product_no              int                            not null,
    product_order_no        int     					   not null,
    product_qty             int     					   not null,
    product_price           int     					   not null,
    comments_status         tinyint default 0	   	       not null,
    foreign key (product_no) references product (product_no),
    foreign key (product_order_no) references product_order (product_order_no)
);

insert into product_order_detail (product_no, product_order_no, product_qty, product_price, comments_status)
values (1,  1001, 1, 250, 0),
       (9,  1001, 1, 100, 1),
       (10, 1001, 2, 900, 1),
       (4,  1002, 1, 150, 0),
       (13, 1005, 2, 500, 1),
       (6,  1007, 2, 440, 0),
       (2,  1007, 1, 350, 1),
       (17, 1008, 2, 400, 0),
       (2,  1009, 1, 350, 1),
       (3,  1009, 2, 600, 0);


create table save_product
(
    save_product_no int not null auto_increment primary key,
    member_no       int not     null,
    product_no      int not     null,
    save_status     tinyint not null,

    foreign key (member_no) references member (member_no),
    foreign key (product_no) references product (product_no)
);

insert into save_product (member_no, product_no, save_status)
values (1, 10, 1),
       (2, 12, 1),
       (3, 9, 0),
       (4, 4, 0),
       (5, 3, 1),
       (6, 11, 0),
       (7, 8, 0),
       (8, 16, 1),
       (9, 13, 1),
       (5, 6, 0);