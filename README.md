# Minesweeper Game

## ชื่อโครงงาน 

```
MineSweeper.--035--078--124--451--710--957
```

## สมาชิก

```

1.นางสาวกานต์ธิดา เปรื่องนนท์ 6730300035
2.นายจักรกริชณ์ มงคลปราภากร 6730300078
3.นายชโยดม ชัยรัตน์ 6730300124
4.นางสาวภัทรธิษณ์ ขามธาตุสิริวัฒน์ 6730300451
5.นางสาวเกดธิดา เกียรติกุล 6730300710
6.นางสาวอรณิชา จันทร์สว่าง 6730300957

```

## รายละเอียดโดยย่อ

Minesweeper นี้ ผู้เล่นสามารถกำหนดจำนวนระเบิดก่อนที่จะเข้าสู่เกมได้ เมื่อเข้าสู่เกมแล้วผู้เล่นจะสามารถเลือกจุดที่ตนเองต้องการได้ ก็คือผู้เล่นสามารถเลือก 	row column ได้ และระหว่างเล่นเกม ผู้เล่นจะสามารถเห็นคะแนนที่ตนเองทำได้ในแต่ละครั้งได้ และเมื่อผู้เล่นคลิกโดน x หรือระเบิดก็จะจบเกมในรอบนั้นๆทันที

### Example

a 9x9 mine field display.

```
.X.......
.....X..X
....X....
......X..
..X......
....X....
..X......
..X......
......X..
```

## Task 2: Load mine field from file.

Implement method `initFromFile(String mineFieldFile)` of `Minesweeper` class to load a mine field from a text file.

### Example

When calling `initMineFieldFromFile("minefield/minefield01.txt")`, the method should read mine field information from the text file and create a `Minesweeper` object.

#### File format:
* The first line is the field width (fieldX)
* The second line is the field height (fieldY)
* The remaining line display the `fieldX` rows by `fieldY` columns mine field.
  * 'X' means that this cell is a mine
  * '.' means that this cell is safe.

```
9   
9   
.X.......   
.....X..X
....X....
......X..
..X......
....X....
..X......
..X......
......X..
```
