# frozen_string_literal: true
require_relative '../irrgarten_ruby/dice'
require_relative 'weapon'
require_relative 'shield'
require_relative 'game_character'
require_relative 'orientation'
require_relative 'directions'
require_relative 'game_state'

class TestP1
  
  def self.test_weapon
    puts "TESTING WEAPON"

    void_weapon = Weapon.new(0,0)
    filled_weapon = Weapon.new(Dice.weapon_power, Dice.uses_left)

    puts "Weapons state after being created: "
    puts "Void weapon: #{void_weapon.to_s}"
    puts "Filled weapon: #{filled_weapon.to_s}"

    attack_power_void_weapon = void_weapon.attack
    attack_power_filled_weapon = filled_weapon.attack

    puts "State after attacking: "
    puts "Void weapon: #{void_weapon.to_s}"
    puts "Attack power of void weapon: #{attack_power_void_weapon}"
    puts "Filled weapon: #{filled_weapon.to_s}"
    puts "Attack power of filled weapon: #{attack_power_filled_weapon.to_s}"

    max_weapon = Weapon.new(2,5)
    min_weapon = void_weapon
    average_weapon = Weapon.new(1,3)

    puts "Result discarding max_weapon: #{max_weapon.discard}"
    puts "Result discarding min_weapon: #{min_weapon.discard}"
    puts "Result discarding average_weapon: #{average_weapon.discard}"
    puts "\n\n"

    0
  end

  def self.test_shield
    puts "TESTING SHIELD"

    void_shield = Shield.new(0,0)
    filled_shield = Shield.new(Dice.shield_power, Dice.uses_left)

    puts "Shields state after being created: "
    puts "Void shield: #{void_shield.to_s}"
    puts "Filled shield: #{filled_shield.to_s}"

    protect_power_void_shield = void_shield.protect
    protect_power_filled_shield = filled_shield.protect

    puts "State after protecting: "
    puts "Void shield: #{void_shield.to_s}"
    puts "Attack power of void shield: #{protect_power_void_shield}"
    puts "Filled shield: #{filled_shield.to_s}"
    puts "Attack power of filled shield: #{protect_power_filled_shield.to_s}"

    max_shield = Shield.new(2,5)
    min_shield = void_shield
    average_shield = Shield.new(1,3)

    puts "Result discarding max_shield: #{max_shield.discard}"
    puts "Result discarding min_shield: #{min_shield.discard}"
    puts "Result discarding average_shield: #{average_shield.discard}"
    puts "\n\n"

    0
  end

  def self.test_game_state
    puts "TESTING GAME STATE"

    gs = GameState.new("Okay", "Antonio, Pepe", "Godzilla", 3, true, "Log")
    puts "Labyrinth state: #{gs.labyrinthv}"
    puts "Players: #{gs.players}"
    puts "Monsters: #{gs.monsters}"
    puts "Current Players: #{gs.current_player}"
    puts "Winner: #{gs.winner}"
    puts "Log: #{gs.log}"
    puts "\n\n"

    0
  end

  def self.test_enums
    puts "TESTING ENUMS"

    gc = GameCharacter::PLAYER
    dir = Directions::UP
    ori = Orientation::HORIZONTAL

    puts "Game Character: #{gc}"
    puts "Directions: #{dir}"
    puts "Orientation: #{ori}"
    puts "\n\n"

    0
  end

  def self.test_dice
    puts "TESTING Dice"

    average_prob_uses = 0
    average_prob_intelligence = 0
    average_prob_strength = 0
    average_prob_resurrect = 0
    average_prob_weapons_reward = 0
    average_prob_shields_reward = 0
    average_prob_health_reward = 0
    average_prob_attack = 0
    average_prob_protect = 0
    average_prob_intensity = 0

    n = 100000

    n.times do
      average_prob_uses += Dice.uses_left
      average_prob_intelligence += Dice.random_intelligence
      average_prob_strength += Dice.random_strength
      average_prob_resurrect += (Dice.resurrect_player) ? 1 : 0
      average_prob_weapons_reward += Dice.weapons_reward
      average_prob_shields_reward += Dice.shields_reward
      average_prob_health_reward += Dice.health_reward
      average_prob_attack += Dice.weapon_power
      average_prob_protect += Dice.shield_power
      average_prob_intensity += Dice.intensity(10)
    end

    average_prob_uses /= n*5.0
    average_prob_intelligence /= n*10.0
    average_prob_strength /= n*10.0
    average_prob_resurrect /= n*1.0
    average_prob_weapons_reward /= n*2.0
    average_prob_shields_reward /= n*3.0
    average_prob_health_reward /= n*5.0
    average_prob_attack /= n*3.0
    average_prob_protect /= n*2.0
    average_prob_intensity /= n*10.0

    puts "Average Prob Uses :  #{average_prob_uses*100}%"
    puts "Average Prob Intelligence :  #{average_prob_intelligence*100}%"
    puts "Average Prob Strength :  #{average_prob_strength*100}%"
    puts "Average Prob Resurrect :  #{average_prob_resurrect*100}%"
    puts "Average Prob Weapons Reward :  #{average_prob_weapons_reward*100}%"
    puts "Average Prob Shields Reward :  #{average_prob_shields_reward*100}%"
    puts "Average Prob HealthReward :  #{average_prob_health_reward*100}%"
    puts "Average Prob Attack :  #{average_prob_attack*100}%"
    puts "Average Prob Protect :  #{average_prob_protect*100}%"
    puts "Average Prob Intensity :  #{average_prob_intensity*100}%"
    puts "\n\n"

    0
  end

  def self.main
    test_weapon + test_shield + test_game_state + test_enums + test_dice
  end
end

TestP1.main
