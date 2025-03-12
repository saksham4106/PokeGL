import requests
import json
import random
s = requests.session()

with open('Pokemons.java', 'utils.a') as f:
    f2 = open('movesData.json', 'r')
    data = json.loads(f2.read())
    for i in range(1, 925):
        url = f"https://pokeapi.co/api/v2/pokemon/{i}"
        pokemon_data = s.get(url, allow_redirects=True).content
        obj = json.loads(pokemon_data)

        name = obj["forms"][0]["name"]
        abilities = []
        height = obj["height"]
        stats = {}
        types = []
        base_level = obj["base_experience"]

        for ability in obj["abilities"]:
            abilities.append(ability["ability"]["name"])
        for stat in obj["stats"]:
            stats[stat["stat"]["name"]] = stat["base_stat"]
        for typ in obj["types"]:
            types.append(typ["type"]["name"])
        type_list = "Arrays.asList("
        for t in types:
            type_list += t.upper() + ", "
        type_list = type_list.rstrip(", ") + ")"

        hp = stats["hp"]
        attack = stats["attack"]
        defense = stats["defense"]
        speed = stats["speed"]

        normal_moves = []
        type1_moves = []
        type2_moves = []
        for j in obj["moves"]:
            move_name = j["move"]["name"]
            try:
                move_gen = data[move_name]["gen"]
                move_type = data[move_name]["type"]
            except:
                continue

            if(int(move_gen) > 6):
                print(move_gen)
                continue

            if(move_type == "normal"):
                normal_moves.append(move_name)
            else:
                if(len(types) == 1):
                    if(types[0] == move_type):
                        type1_moves.append(move_name)

                elif(len(types) == 2):
                    if(move_type == types[0]):
                        type1_moves.append(move_name)
                    elif(move_type == types[1]):
                        type2_moves.append(move_name)


        normal_rand_moves = "Arrays.asList("
        type1_rand_moves = "Arrays.asList("
        type2_rand_moves = "Arrays.asList("
        for k in range(4):
            if(len(normal_moves ) > 0):
                if len(normal_moves ) > 1:
                    n = random.randint(0, len(normal_moves ) - 1)
                    m = list(normal_moves )[n]
                    normal_rand_moves += f'{m.upper()}, '
                else:
                    n = 0
                    m = list(normal_moves )[n]
                    normal_rand_moves += f'{m.upper()}, '
                    break

        for k in range(4):
            if(len(type1_moves ) > 0):
                if len(type1_moves ) > 1:
                    n = random.randint(0, len(type1_moves ) - 1)
                    m = list(type1_moves )[n]
                    type1_rand_moves += f'{m.upper()}, '
                else:
                    n = 0
                    m = list(type1_moves )[n]
                    type1_rand_moves += f'{m.upper()}, '
                    break

        for k in range(4):
            if(len(type2_moves ) > 0):
                if len(type2_moves ) > 1:
                    n = random.randint(0, len(type2_moves ) - 1)
                    m = list(type2_moves )[n]
                    type2_rand_moves += f'{m.upper()}, '
                else:
                    n = 0
                    m = list(type2_moves )[n]
                    type2_rand_moves += f'{m.upper()}, '
                    break

        normal_rand_moves = normal_rand_moves.rstrip(', ') + ")"
        type1_rand_moves = type1_rand_moves.rstrip(', ') + ")"
        type2_rand_moves = type2_rand_moves.rstrip(', ') + ")"

        f.write(f'public static Pokemon {name.upper()} = new Pokemon({i}, "{name}", {height}, {hp}, {attack}, {defense}, {speed}, {base_level}, {type_list}, {normal_rand_moves}, {type1_rand_moves}, {type2_rand_moves});\n')



# import requests
# import json
# s = requests.session()

# with open('PokemonAbilities.java', 'utils.a') as f:
#     for i in range(234, 368):
#         url = f"https://pokeapi.co/api/v2/ability/{i}"
#         pokemon_data = s.get(url, allow_redirects=True).content
#         obj = json.loads(pokemon_data)
#         name = obj["name"]
#         try:
#             if len(obj["effect_entries"]) > 1:
#                 effect_desc = obj["effect_entries"][1]["short_effect"]
#             else:
#                 effect_desc = obj["effect_entries"][0]["short_effect"]
#             print(i)
#             f.write(f'public static PokemonAbility {name.upper()} = new PokemonAbility({i}, "{name}", "{effect_desc}");\n')
#         except:
#             print()


#
# import requests
# import json
# s = requests.session()
#
# with open('movesData.json', 'utils.a') as f:
#     data = {}
#     try:
#         for i in range( 1, 620):
#             url = f"https://pokeapi.co/api/v2/move/{i}"
#             pokemon_data = s.get(url, allow_redirects=True).content
#             obj = json.loads(pokemon_data)
#             name = obj["name"]
#             accuracy = obj["accuracy"]
#             power = obj["power"]
#             pp = obj["pp"]
#             priority = obj["priority"]
#             typ = obj["type"]["name"]
#             effect = obj["effect_entries"][0]["short_effect"]
#             gen = obj["generation"]["url"].rstrip("/")[-1]
#             data[name] = {"type": typ, "gen": gen}
#     finally:
#         json.dump(data, f, indent = 4)
#
#
#
#
