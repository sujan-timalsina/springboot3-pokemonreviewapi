package com.pokemonreview.service.implementation;

import com.pokemonreview.dto.PokemonResponse;
import com.pokemonreview.repository.PokemonRepository;
import com.pokemonreview.dto.PokemonDto;
import com.pokemonreview.exception.ResourceNotFoundException;
import com.pokemonreview.model.Pokemon;
import com.pokemonreview.service.PokemonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonServiceImpl implements PokemonService {

    private PokemonRepository pokemonRepository;

    public PokemonServiceImpl(PokemonRepository pokemonRepository){
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public PokemonDto createPokemon(PokemonDto pokemonDto){
        Pokemon pokemon = new Pokemon();
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());

        Pokemon newPokemon = pokemonRepository.save(pokemon);
        return mapToDto(newPokemon);
    }

    @Override
    public PokemonResponse getAllPokemon(int pageNo, int pageSize){
        PageRequest pageable = PageRequest.of(pageNo, pageSize);
        Page<Pokemon> pokemons= pokemonRepository.findAll(pageable);
        List<Pokemon> listOfPokemon = pokemons.getContent();
        List<PokemonDto> content = listOfPokemon.stream().map(pokemon -> mapToDto(pokemon)).collect(Collectors.toList());

        PokemonResponse pokemonResponse = new PokemonResponse();
        pokemonResponse.setContent(content);
        pokemonResponse.setPageNo(pokemons.getNumber());
        pokemonResponse.setPageSize(pokemons.getSize());
        pokemonResponse.setTotalPages(pokemons.getTotalPages());
        pokemonResponse.setTotalElements((int) pokemons.getTotalElements());
        pokemonResponse.setLast(pokemons.isLast());
        return pokemonResponse;
    }

    @Override
    public PokemonDto getPokemonById(int id){
        Pokemon pokemon = pokemonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pokemon doesn't exist of id: "+id));
        return mapToDto(pokemon);
    }

    @Override
    public PokemonDto updatePokemon(PokemonDto pokemonDto, int id) {
        Pokemon pokemon = pokemonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pokemon doesn't exist of id: "+id+" for the update"));
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());

        Pokemon updatedPokemon = pokemonRepository.save(pokemon);
        return mapToDto(updatedPokemon);
    }

    @Override
    public void deletePokemonById(int id) {
        Pokemon pokemon = pokemonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pokemon doesn't exist of id: "+id+" for the deletion"));
        pokemonRepository.delete(pokemon);
    }

    private PokemonDto mapToDto(Pokemon pokemon){
        PokemonDto pokemonDto = new PokemonDto();
        pokemonDto.setId(pokemon.getId());
        pokemonDto.setName(pokemon.getName());
        pokemonDto.setType(pokemon.getType());
        return pokemonDto;
    }

    private Pokemon mapToEntity(PokemonDto pokemonDto){
        Pokemon pokemon = new Pokemon();
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());
        return pokemon;
    }
}
//map returns a new list rather than only looping
//Changed from <Pokemon> generic list to <PokemonDto> typed list on method getAllPokemon() using stream, map and collect