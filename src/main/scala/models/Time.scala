package models

case class Time(minutes: Int, seconds: Int) {
  override def toString: String = f"$minutes%02d:$seconds%02d"
}
