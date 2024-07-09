import requests
import json 
import random 
s = requests.session()

with open('pokemons.json', 'a') as f:
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

        hp = stats["hp"]
        attack = stats["attack"]
        defense = stats["defense"]
        speed = stats["speed"]
