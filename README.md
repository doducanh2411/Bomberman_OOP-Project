# Bài tập lớn OOP (2022-2023) - Bomberman Game
Sinh viên: Ngũ Thành An _ Mã sinh viên: 21020271

Sinh viên: Đỗ Đức Anh _ Mã sinh viên: 21020274

## Mô tả về các đối tượng trong trò chơi

### Game UML:

![](res/umlDiagram.png)

Nếu bạn đã từng chơi Bomberman, bạn sẽ cảm thấy quen thuộc với những đối tượng này. Chúng được được chia làm hai loại chính là nhóm đối tượng động (*Bomber*, *Enemy*, *Bomb*) và nhóm đối tượng tĩnh (*Grass*, *Wall*, *Brick*, *Door*, *Item*).

*Hãy thiết kế hệ thống các đối tượng một cách phù hợp để tận dụng tối đa sức mạnh của OOP: tái sử dụng code, dễ dàng maintain.*

- ![](res/sprites/player_down.png) *Bomber* là nhân vật chính của trò chơi. Bomber có thể di chuyển theo 4 hướng trái/phải/lên/xuống theo sự điều khiển của người chơi.
- ![](res/sprites/balloom_left1.png) *Enemy* là các đối tượng mà Bomber phải tiêu diệt hết để có thể qua Level. Enemy có thể di chuyển ngẫu nhiên hoặc tự đuổi theo Bomber tùy theo loại Enemy. Các loại Enemy sẽ được mô tả cụ thể ở phần dưới.
- ![](res/sprites/bomb.png) *Bomb* là đối tượng mà Bomber sẽ đặt và kích hoạt tại các ô Grass. Khi đã được kích hoạt, Bomber và Enemy không thể di chuyển vào vị trí Bomb. Tuy nhiên ngay khi Bomber vừa đặt và kích hoạt Bomb tại ví trí của mình, Bomber có một lần được đi từ vị trí đặt Bomb ra vị trí bên cạnh. Sau khi kích hoạt 2s, Bomb sẽ tự nổ, các đối tượng *Flame* ![](res/sprites/explosion_horizontal.png) được tạo ra.


- ![](res/sprites/grass.png) *Grass* là đối tượng mà Bomber và Enemy có thể di chuyển xuyên qua, và cho phép đặt Bomb lên vị trí của nó
- ![](res/sprites/wall.png) *Wall* là đối tượng cố định, không thể phá hủy bằng Bomb cũng như không thể đặt Bomb lên được, Bomber và Enemy không thể di chuyển vào đối tượng này
- ![](res/sprites/brick.png) *Brick* là đối tượng được đặt lên các ô Grass, không cho phép đặt Bomb lên nhưng có thể bị phá hủy bởi Bomb được đặt gần đó. Bomber và Enemy thông thường không thể di chuyển vào vị trí Brick khi nó chưa bị phá hủy.


- ![](res/sprites/portal.png) *Portal* là đối tượng được giấu phía sau một đối tượng Brick. Khi Brick đó bị phá hủy, Portal sẽ hiện ra và nếu tất cả Enemy đã bị tiêu diệt thì người chơi có thể qua Level khác bằng cách di chuyển vào vị trí của Portal.

Các *Item* cũng được giấu phía sau Brick và chỉ hiện ra khi Brick bị phá hủy. Bomber có thể sử dụng Item bằng cách di chuyển vào vị trí của Item. Thông tin về chức năng của các Item được liệt kê như dưới đây:
- ![](res/sprites/powerup_speed.png) *SpeedItem* Khi sử dụng Item này, Bomber sẽ được tăng vận tốc di chuyển thêm một giá trị thích hợp
- ![](res/sprites/powerup_flames.png) *FlameItem* Item này giúp tăng phạm vi ảnh hưởng của Bomb khi nổ (độ dài các Flame lớn hơn)
- ![](res/sprites/powerup_bombs.png) *BombItem* Thông thường, nếu không có đối tượng Bomb nào đang trong trạng thái kích hoạt, Bomber sẽ được đặt và kích hoạt duy nhất một đối tượng Bomb. Item này giúp tăng số lượng Bomb có thể đặt thêm một.

Có nhiều loại Enemy trong Bomberman, tuy nhiên trong phiên bản này chỉ yêu cầu cài đặt hai loại Enemy dưới đây (nếu cài đặt thêm các loại khác sẽ được cộng thêm điểm):
- ![](res/sprites/balloom_left1.png) *Balloom* là Enemy đơn giản nhất, di chuyển ngẫu nhiên với vận tốc cố định
- ![](res/sprites/oneal_left1.png) *Oneal* có tốc độ di chuyển thay đổi, lúc nhanh, lúc chậm và di chuyển "thông minh" hơn so với Balloom (biết đuổi theo Bomber)
- ![](res/sprites/doll_left1.png) *Doll* có tốc độ di chuyển thay đổi và có thể di chuyển xuyên qua Brick
- ![](res/sprites/kondoria_left1.png) *Kondoria* có tốc độ di chuyển thay đổi và có thể di chuyển xuyên qua cả Brick và Wall

## Mô tả game play, xử lý va chạm và xử lý bom nổ

- Bắt đầu một màn chơi, người chơi điều khiển Bomber di chuyển đặt và kích hoạt Bomb sao cho có thể tiêu diệt hết các Enemy. Đồng thời cũng phải đi phá các Brick tìm vị trí Portal để có thể qua màn khác
- Bomber sẽ bị tiêu diệt khi đứng trong vùng Bomb nổ hoặc va chạm với Enemy. Người chơi có 3 mạng trong 1 màn, khi chết cả 3 mạng, game kết thúc
- Một đối tượng thuộc phạm vi Bomb nổ có nghĩa là đối tượng đó va chạm với một trong các tia lửa được tạo ra tại thời điểm một đối tượng Bomb nổ.
- Khi Bomb nổ, một Flame trung tâm![](res/sprites/bomb_exploded.png) tại vị trí Bomb nổ và bốn Flame tại bốn vị trí ô đơn vị xung quanh vị trí của Bomb xuất hiện theo bốn hướng trên![](res/sprites/explosion_vertical.png)/dưới![](res/sprites/explosion_vertical.png)/trái![](res/sprites/explosion_horizontal.png)/phải![](res/sprites/explosion_horizontal.png). Độ dài bốn Flame xung quanh mặc định là 1 đơn vị, được tăng lên khi Bomber sử dụng các FlameItem.
- Khi các Flame xuất hiện, nếu có một đối tượng thuộc loại Brick/Wall nằm trên vị trí một trong các Flame thì độ dài Flame đó sẽ được giảm đi để sao cho Flame chỉ xuất hiện đến vị trí đối tượng Brick/Wall theo hướng xuất hiện. Lúc đó chỉ có đối tượng Brick/Wall bị ảnh hưởng bởi Flame, các đối tượng tiếp theo không bị ảnh hưởng.

## Demo game
![](res/demo/menu.png)
![](res/demo/gameplay1.png)
![](res/demo/gameplay2.png)
![](res/demo/ranking.png)

## Chức năng đã làm theo yêu cầu
- Thiết kế cây thừa kế cho các đối tượng game
- Xây dựng bản đồ màn chơi từ tệp cấu hình.
- Di chuyển Bomber theo sự điều khiển từ người chơi.
- Tự động di chuyển các Enemy.
- Xử lý va chạm cho các đối tượng Bomber, Enemy, Wall, Brick, Bomb.
- Xử lý bom nổ.
- Xử lý khi Bomber sử dụng các Item và khi đi vào vị trí Portal.
- Nâng cấp thuật toán tìm đường cho Enemy.
- Cài đặt thêm các loại Enemy khác.
- Xử lý hiệu ứng âm thanh (thêm music & sound effects).
- Thêm điểm cho các người chơi
