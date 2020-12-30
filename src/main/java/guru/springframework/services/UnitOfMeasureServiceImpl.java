package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Spliterators.spliterator;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository
            , UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {
        Set<UnitOfMeasureCommand> uomCommandSet = new HashSet<>();
        Iterable<UnitOfMeasure> uomSet = unitOfMeasureRepository.findAll();
        uomSet.forEach(uom -> uomCommandSet.add(unitOfMeasureToUnitOfMeasureCommand.convert(uom)));

//        uomCommandSet = StreamSupport.stream(uomSet.spliterator(),false)
//                .map(uom -> unitOfMeasureToUnitOfMeasureCommand.convert(uom))
//                .collect(Collectors.toSet());

        return uomCommandSet;
    }
}
