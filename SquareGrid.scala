package Project1

class SquareGrid (var xLength: Int, var yLength: Int){
  var squares: Array[Array[Square]] = populateGrid()

  def populateGrid(): Array[Array[Square]] ={
    Array.tabulate(xLength, yLength) {(x ,y) => new Square(x, y)}
  }

  def getSquare(x: Int, y: Int): Square = {
    assert(x < xLength)
    assert(y < yLength)
    squares(x)(y)
  }

  def getNBAliveNeighborSquares(square: Square): Int = {
    val xPosition: Int = square.xPosition
    val yPosition: Int = square.yPosition

    var counter: Int = 0

    for (x <- xPosition - 1 to xPosition + 1) {
      for (y <- yPosition - 1 to yPosition + 1) {
        if (isASquare(x, y) && !( x == xPosition && y == yPosition) && squares(x)(y).isAlive) {
          counter += 1
        }
      }
    }
    counter
  }

  def nextGeneration(): Unit = {
    val nextSquares = squares.map(_.map(_.deepClone()))

    for(x: Int <- squares.indices){
      for(y: Int <- squares(x).indices){
        val square = squares(x)(y)
        val aliveNeighbor = getNBAliveNeighborSquares(square)
        if(square.isAlive && aliveNeighbor < 2){
          nextSquares(x)(y).changeState()
        }else if(square.isAlive && (aliveNeighbor == 2 || aliveNeighbor == 3)){

        }else if(square.isAlive && aliveNeighbor > 3){
          nextSquares(x)(y).changeState()
        }else if(!square.isAlive && aliveNeighbor == 3){
          nextSquares(x)(y).changeState()
        }
      }
    }
    squares = nextSquares
  }

  def isASquare(x: Int, y: Int): Boolean = {
    try {
      squares(x)(y)
      true
    }catch {
      case  e:ArrayIndexOutOfBoundsException => false
    }
  }

}
