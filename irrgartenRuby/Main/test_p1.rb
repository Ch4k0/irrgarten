require_relative '../Control/controller'
require_relative '../UI/textUI'
require_relative '../Game'

  game = Irrgarten::Game.new(3)

    view = Irrgarten::UI::TextUI.new

    controller = Irrgarten::Control::Controller.new(game, view)

    controller.play

    puts "Game over!"