#!/bin/bash 

for d in */ ; do
	IN="$d"
	arrIn=(${IN//_/ })
	pokemonName=${arrIn[1]}
	updatedPok=${pokemonName::-1}
	mv "${IN}/frontTexture.png" "../pokemonImage/${updatedPok}_frontTexture.png"
done

