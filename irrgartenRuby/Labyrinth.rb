require_relative 'Orientation'

module Irrgarten
  class Labyrinth
    @@BLOCKCHAR   = 'X'
    @@EMPTYCHAR   = '-'
    @@MONSTERCHAR = 'M'
    @@COMBATCHAR  = 'C'
    @@EXITCHAR    = 'E'
    @@ROW = 0
    @@COL = 1

    def initialize(n_rows, n_cols, exit_row, exit_col)
      @n_rows = n_rows
      @n_cols = n_cols
      @exit_row = exit_row
      @exit_col = exit_col

      @players = Array.new(n_rows) { Array.new(n_cols) }
      @monsters = Array.new(n_rows) { Array.new(n_cols) }
      @labyrinth = Array.new(n_rows) { Array.new(n_cols, @@EMPTYCHAR) }

      (0...n_rows).each do |i|
        (0...n_cols).each do |j|
          if i == 0 || j == 0 || i == n_rows - 1 || j == n_cols - 1
            @labyrinth[i][j] = @@BLOCKCHAR
          else
            @labyrinth[i][j] = @@EMPTYCHAR
          end
        end
      end
      @labyrinth[@exit_row][@exit_col] = @@EXITCHAR if pos_ok?(@exit_row, @exit_col)

    end

    def spread_players(players)
      for i in 0...players.size
        p = players[i]
        pos = random_empty_pos
        monster = put_player_2d(-1,-1, pos[@@ROW], pos[@@COL], p)
      end
    end

    def have_a_winner?
      !@players[@exit_row][@exit_col].nil?
    end

    def to_s
      resultado = "\tEstado del Laberinto #{@n_rows}x#{@n_cols}\n\n"

      (0...@n_rows).each do |i|
        (0...@n_cols).each do |j|
          resultado += "#{@labyrinth[i][j]} "
        end
        resultado += "\n"
      end

      resultado
    end

    def add_monster(row, col, monster)
      if pos_ok?(row, col) && empty_pos?(row, col)
        @labyrinth[row][col] = @@MONSTERCHAR
        monster.set_pos(row, col)
        @monsters[row][col] = monster
      end
    end

     def put_player(direction, player)
       old_row = player.row
       old_col = player.col

       new_pos = dir2pos(old_row, old_col,direction)
       monster = put_player_2d(old_row, old_col, new_pos[@@ROW], new_pos[@@COL], player)

       monster
     end

     def add_block(orientation, start_row, start_col, length)
       if orientation == Orientation::VERTICAL
         incRow = 1
         incCol = 0
       else
         incRow = 0
         incCol = 1
       end

       row = start_row
       col = start_col

       while pos_ok?(row,col) && empty_pos?(row,col) && length > 0
         @labyrinth[row][col] = @@BLOCKCHAR
         length -= 1
         row += incRow
         col += incCol
       end

     end

     def valid_moves(row, col)
       moves = []

       moves << :down  if can_step_on?(row + 1, col)
       moves << :up    if can_step_on?(row - 1, col)
       moves << :right if can_step_on?(row, col + 1)
       moves << :left  if can_step_on?(row, col - 1)

       moves
     end

    private

    def pos_ok?(row, col)
      row.between?(0, @n_rows - 1) && col.between?(0, @n_cols - 1)
    end

    def empty_pos?(row, col)
      if pos_ok?(row, col)
        @labyrinth[row][col] == @@EMPTYCHAR
      end
    end

    def monster_pos?(row, col)
      !@monsters[row][col].nil?
    end

    def exit_pos?(row, col)
      row == @exit_row && col == @exit_col
    end

    def combat_pos?(row, col)
      @labyrinth[row][col] == @@COMBATCHAR
    end

    def can_step_on?(row, col)
      pos_ok?(row, col) && empty_pos?(row, col) || monster_pos?(row, col) || exit_pos?(row, col)
    end

    def update_old_pos(row, col)
      if pos_ok?(row, col)
        if @labyrinth[row][col] == @@COMBATCHAR
          @labyrinth[row][col] = @@MONSTERCHAR
        else
          @labyrinth[row][col] = @@EMPTYCHAR
        end
      end
    end

    def dir2pos(row, col, direction)
      case direction
      when :up
        [row - 1, col]
      when :down
        [row + 1, col]
      when :left
        [row, col - 1]
      when :right
        [row, col + 1]
      else
        [row, col]
      end
    end

    def random_empty_pos
      loop do
        new_row = Dice.random_pos(@n_rows)
        new_col = Dice.random_pos(@n_cols)
        return [new_row, new_col] if empty_pos?(new_row, new_col)
      end
    end

    def put_player_2d(old_row, old_col, row, col, player)
      if can_step_on?(row, col)
        if pos_ok?(row, col)
          p = @players[old_row][old_col]
          if p == player
            update_old_pos(old_row, old_col)
            @players[old_row][old_col] = nil
          end
        end
        if monster_pos?(row, col)
          @labyrinth[row][col] = @@COMBATCHAR
          output = @monsters[row][col]
        else
          number = player.number
          @labyrinth[row][col] = number;
        end
        @players[row][col] = player
        player.set_pos(row, col)
      end
      output
    end
  end
end