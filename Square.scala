package GameOfLife

class Square (var xPosition: Int ,var yPosition: Int){
  var isAlive: Boolean = false

  def changeState(): Unit = {
    isAlive = !isAlive
  }

  def deepClone(): Square = {
    val clonedSquare = new Square(this.xPosition, this.yPosition)
    clonedSquare.isAlive = this.isAlive
    clonedSquare
  }
}
