package GameOfLife

import hevs.graphics.FunGraphics

import java.awt.Color
import java.awt.event.{KeyEvent, KeyListener, MouseEvent, MouseListener, MouseMotionListener}

object Runner extends App{
  val GRAPHICS_WIDTH: Int = 900
  val GRAPHICS_HEIGHT: Int = 600
  val display: FunGraphics = new FunGraphics(GRAPHICS_WIDTH, GRAPHICS_HEIGHT)

  val BACKGROUND_COLOR: Color = Color.black
  val GRID_SQUARE_SIZE: Int = 15
  val GRID_SQUARE_BORDER_COLOR: Color = Color.white
  var SQUARE_COLOR: Color = Color.white

  val SquareGrid: SquareGrid = new SquareGrid(GRAPHICS_WIDTH / GRID_SQUARE_SIZE , GRAPHICS_HEIGHT / GRID_SQUARE_SIZE)

  var eraseSquare: Boolean = false

  val MOUSE_LISTENER = new MouseListener {

    override def mouseClicked(e: MouseEvent): Unit = {
    }

    override def mousePressed(e: MouseEvent): Unit = {

      val squareX: Int = e.getX / GRID_SQUARE_SIZE
      val squareY: Int = e.getY / GRID_SQUARE_SIZE

      val square = SquareGrid.getSquare(squareX,squareY)

      // Check the state of the rectangle clicked
      if(square.isAlive){
        display.setColor(BACKGROUND_COLOR)
      }else{
        display.setColor(SQUARE_COLOR)
      }

      // TODO Draw a smaller rectangle if it's on the last line / row
      // + 1 and -1 are used to not draw the rectangle on the grid vertical and horizontal lines
      display.drawFillRect(squareX * GRID_SQUARE_SIZE + 1, squareY * GRID_SQUARE_SIZE + 1, GRID_SQUARE_SIZE - 1 , GRID_SQUARE_SIZE - 1)
      square.changeState()
    }

    override def mouseReleased(e: MouseEvent): Unit = {
    }

    override def mouseEntered(e: MouseEvent): Unit = {
    }

    override def mouseExited(e: MouseEvent): Unit = {
    }
  }

  val MOUSE_MOTION_LISTENER = new MouseMotionListener {
    override def mouseDragged(e: MouseEvent): Unit = {

      if (e.getX < GRAPHICS_WIDTH && e.getY < GRAPHICS_HEIGHT && e.getX > 0 && e.getY > 0){
          val squareX: Int = e.getX / GRID_SQUARE_SIZE
        val squareY: Int = e.getY / GRID_SQUARE_SIZE

        val square = SquareGrid.getSquare(squareX, squareY)

        // Check the state of the rectangle clicked
        if (eraseSquare) {
          display.setColor(BACKGROUND_COLOR)

          // TODO Draw a smaller rectangle if it's on the last line / row
          display.drawFillRect(squareX * GRID_SQUARE_SIZE + 1, squareY * GRID_SQUARE_SIZE + 1, GRID_SQUARE_SIZE - 1, GRID_SQUARE_SIZE - 1)
          square.isAlive = false

        } else {
          display.setColor(SQUARE_COLOR)

          // TODO Draw a smaller rectangle if it's on the last line / row
          display.drawFillRect(squareX * GRID_SQUARE_SIZE + 1, squareY * GRID_SQUARE_SIZE + 1, GRID_SQUARE_SIZE - 1, GRID_SQUARE_SIZE - 1)
          square.isAlive = true
        }
      }

    }

    override def mouseMoved(e: MouseEvent): Unit = {

    }
  }

  val keyListener = new KeyListener {

    override def keyTyped(e: KeyEvent): Unit = {

    }

    override def keyPressed(e: KeyEvent): Unit = {
      if (e.getKeyCode == KeyEvent.VK_SPACE) {
        SquareGrid.nextGeneration()
        val random = new scala.util.Random
        val red = random.nextInt(256)
        val green = random.nextInt(256)
        val blue = random.nextInt(256)
        SQUARE_COLOR = new Color(red, green, blue)
        drawSquares()
      }
      if (e.getKeyCode == KeyEvent.VK_S) {
        eraseSquare = !eraseSquare
      }
    }

    override def keyReleased(e: KeyEvent): Unit = {
    }
  }

  display.addMouseListener(MOUSE_LISTENER)
  display.addMouseMotionListener(MOUSE_MOTION_LISTENER)
  display.setKeyManager(keyListener)


  def setBackGround(): Unit = {
    display.clear(BACKGROUND_COLOR)
  }

  // TODO Add the last lines
  def drawGrid(): Unit = {

    display.setColor(GRID_SQUARE_BORDER_COLOR)

    // Draw the vertical lines on the display
    for(i: Int <- 0 to GRAPHICS_WIDTH by GRID_SQUARE_SIZE){
      display.drawLine(i, 0, i, GRAPHICS_HEIGHT)
    }

    // Draw the horizontal lines on the display
    for(i: Int <- 0 to GRAPHICS_HEIGHT by GRID_SQUARE_SIZE){
      display.drawLine(0, i, GRAPHICS_WIDTH, i)
    }
  }

  def drawSquares(): Unit ={
    for (i: Int <- SquareGrid.squares.indices){
      for(square: Square <- SquareGrid.squares(i)){
        if (square.isAlive) {
          display.setColor(SQUARE_COLOR)
        } else {
          display.setColor(BACKGROUND_COLOR)
        }

        // TODO Draw a smaller rectangle if it's on the last line / row
        // + 1 and -1 are used to not draw the rectangle on the grid vertical and horizontal lines
        display.drawFillRect(square.xPosition * GRID_SQUARE_SIZE + 1, square.yPosition * GRID_SQUARE_SIZE + 1, GRID_SQUARE_SIZE - 1, GRID_SQUARE_SIZE - 1)
      }
    }
  }

  setBackGround()
  drawGrid()
}
