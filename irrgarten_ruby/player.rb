# frozen_string_literal: true

class Player
  MAX_WEAPONS = 2
  MAX_SHIELDS = 3
  INITIAL_HEALTH = 10
  HITS2LOSE = 3

  def initialize (number, intelligence, strength)
    @number = number
    @intelligence = intelligence
    @strength = strength
    @consecutive_hits = 0
    @health = INITIAL_HEALTH
    @name = "Player ##{@number}"
    @weapons = Array.new
    @shields = Array.new
    @row = -1
    @col = -1
  end

  attr_reader :row
  attr_reader :col
  attr_reader :number

  def resurrect
    @health = INITIAL_HEALTH
    @consecutive_hits = 0
    @weapons.clear
    @shields.clear
  end

  def set_pos (row, col)
    @row = row
    @col = col
  end

  def dead
    @health == 0
  end

  def move (direction, valid_moves)
    size = valid_moves.size
    contained = valid_moves.include?(direction)

    if (size > 0) && (!contained)
      valid_moves[0]
    else
      direction
    end
  end

  def attack
    @strength + sum_weapons
  end

  def defend (received_attack)
    manage_hit(received_attack)
  end

  def receive_reward
    w_reward = Dice.weapons_reward
    s_reward = Dice.shields_reward

    w_reward.each do
      w_new = Weapon.new
      receive_weapon(w_new)
    end

    s_reward.each do
      s_new = Shield.new
      receive_shield(s_new)
    end

    @health += Dice.health_reward
  end

  # @return [String]
  def to_s
    header_string = "PLAYER: #{@name}\n"
    location_string = "POSITION: [#{row}, #{col}]\n"
    strength_string = "STRENGTH: #{@strength}\n"
    health_string = "HEALTH: #{@health}\n"
    intelligence_string = "INTELLIGENCE: #{@intelligence}\n"
    consecutive_hits_string = "CONSECUTIVE HITS: #{@consecutive_hits}\n"
    weapons_string = ""
    shields_string = ""

    @weapons.each { |w| weapons_string.concat(w.to_s.join("\n")) }
    @shields.each { |s| shields_string.concat(s.to_s.join("\n")) }

    header_string + location_string + strength_string + health_string + intelligence_string + consecutive_hits_string
    + weapons_string + shields_string
  end

  private
  def receive_weapon (w)
    @weapons.each do |wi|
      discard = wi.discard
      if discard
        @weapons.delete(wi)
      end
    end
    
    size = @weapons.size
    if size < MAX_WEAPONS
      @weapons.push(w)
    end
  end

  def receive_shield (s)
    @shields.each do |si|
      discard = si.discard
      if discard
        @shields.delete(si)
      end
    end

    size = @shields.size
    if size < MAX_SHIELDS
      @shields.push(s)
    end
  end

  def new_weapon
    Weapon.new(Dice.weapon_power, Dice.uses_left)
  end

  def new_shield
    Shield.new(Dice.shield_power, Dice.uses_left)
  end

  def sum_weapons
    sum = 0.0
    @weapons.each do |wi|
      sum += wi.attack
    end
    sum
  end

  def sum_shields
    sum = 0.0
    @shields.each do |si|
      sum += si.protect
    end
    sum
  end

  def defensive_energy
    @intelligence + sum_shields
  end

  def manage_hit (received_attack)
    defense = defensive_energy
    lose = false

    if defense < received_attack
      got_wounded
      inc_consecutive_hits
    else
      reset_hits
    end

    if (@consecutive_hits == HITS2LOSE) || dead
      reset_hits
      lose = true
    end

    lose
  end

  def reset_hits
    @consecutive_hits = 0
  end

  def got_wounded
    @health -= 1
  end

  def inc_consecutive_hits
    @consecutive_hits += 1
  end
end
