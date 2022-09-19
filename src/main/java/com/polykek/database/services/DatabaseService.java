package com.polykek.database.services;

import com.polykek.database.entities.*;
import com.polykek.database.repositories.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;
import org.springframework.stereotype.Service;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DatabaseService {
    private final CellAutomatonRepository cellAutomatonRepository;
    private final CellRepository cellRepository;
    private final FinalStateRepository finalStateRepository;
    private final GridRepository gridRepository;
    private final IntermediateStateRepository intermediateStateRepository;
    private final PartitionRepository partitionRepository;
    private final ScaRepository scaRepository;
    private final ScienceRepository scienceRepository;
    private final StartStateRepository startStateRepository;
    private final StepRepository stepRepository;
    private final StructureRepository structureRepository;
    private final TransformRuleRepository transformRuleRepository;
    private final EntitySaver entitySaver;

    private void deleteAllData() {
        stepRepository.deleteAll();
        cellAutomatonRepository.deleteAll();
        cellRepository.deleteAll();
        finalStateRepository.deleteAll();
        gridRepository.deleteAll();
        intermediateStateRepository.deleteAll();
        partitionRepository.deleteAll();
        scaRepository.deleteAll();
        scienceRepository.deleteAll();
        startStateRepository.deleteAll();
        structureRepository.deleteAll();
        transformRuleRepository.deleteAll();
    }
    public void fillData() {
        deleteAllData();

        List<TransformRule> transformRules = fillTransformRule(200);
        List<StartState> startStates = fillStartState(200);
        List<Grid> grids = fillGrid(200);

        List<CellAutomaton> cellAutomatons = fillCellAutomaton(200, transformRules, startStates, grids);

        List<Science> sciences = fillScience(200);
        fillSca(200, sciences, cellAutomatons);

    }

//    private void fillTransformRule(int count, String className) throws ClassNotFoundException {
//        Class c = Class.forName(className);
//        PodamFactory factory = new PodamFactoryImpl();
//        List<Object> transformRules = new ArrayList<>(count);
//
//        for (int i = 0; i < count; i++)
//            transformRules.add(factory.manufacturePojo(c));
//
//        transformRuleRepository.saveAll(transformRules);
//        return;
//    }

    private List<TransformRule> fillTransformRule(int count) {
        PodamFactory factory = new PodamFactoryImpl();
        List<TransformRule> transformRules = new ArrayList<>(count);

        for (int i = 0; i < count; i++)
            transformRules.add(factory.manufacturePojo(TransformRule.class));

        transformRuleRepository.saveAll(transformRules);

        return transformRules;
    }

    private List<Cell> fillCell(int count) {
        PodamFactory factory = new PodamFactoryImpl();
        List<Cell> cells = new ArrayList<>(count);

        for (int i = 0; i < count; i++)
            cells.add(factory.manufacturePojo(Cell.class));

        cellRepository.saveAll(cells);

        return cells;
    }

    private List<CellAutomaton> fillCellAutomaton(int count,
                                                  List<TransformRule> transformRules,
                                                  List<StartState> startStates,
                                                  List<Grid> grids) {
        PodamFactory factory = new PodamFactoryImpl();
        List<CellAutomaton> cellAutomatons = new ArrayList<>();

        NormalDistribution normalDistribution = new NormalDistribution(1, 0.3);

        for (int i = 0; i < count; i++) {
            double xCoord = (double) i/100; //count / 2
            long childCount = Math.round(normalDistribution.density(xCoord) * 100);
            for (int j = 0; j < childCount; j++) {
                CellAutomaton current = factory.manufacturePojo(CellAutomaton.class);
                current.setStartState(startStates.get(i));
                current.setTransformRule(transformRules.get(i));
                current.setGrid(grids.get(i));
                cellAutomatons.add(current);
            }
        }

        cellAutomatonRepository.saveAll(cellAutomatons);

        return cellAutomatons;
    }

    private List<FinalState> fillFinalState(int count) {
        PodamFactory factory = new PodamFactoryImpl();
        List<FinalState> finalStates = new ArrayList<>(count);

        for (int i = 0; i < count; i++)
            finalStates.add(factory.manufacturePojo(FinalState.class));

        finalStateRepository.saveAll(finalStates);

        return finalStates;
    }

    private List<Grid> fillGrid(int count) {
        PodamFactory factory = new PodamFactoryImpl();
        List<Grid> grids = new ArrayList<>(count);

        for (int i = 0; i < count; i++)
            grids.add(factory.manufacturePojo(Grid.class));

        gridRepository.saveAll(grids);

        return grids;
    }

    private List<IntermediateState> fillIntermediateState(int count) {
        PodamFactory factory = new PodamFactoryImpl();
        List<IntermediateState> intermediateStates = new ArrayList<>(count);

        for (int i = 0; i < count; i++)
            intermediateStates.add(factory.manufacturePojo(IntermediateState.class));

        intermediateStateRepository.saveAll(intermediateStates);

        return intermediateStates;
    }

    private List<Partition> fillPartition(int count) {
        PodamFactory factory = new PodamFactoryImpl();
        List<Partition> partitions = new ArrayList<>(count);

        for (int i = 0; i < count; i++)
            partitions.add(factory.manufacturePojo(Partition.class));

        partitionRepository.saveAll(partitions);

        return partitions;
    }

    private List<SCA> fillSca(int count, List<Science> sciences, List<CellAutomaton> cellAutomatons) {
        PodamFactory factory = new PodamFactoryImpl();
        List<SCA> scas = new ArrayList<>(count);
        int[] lengths = {sciences.size(), cellAutomatons.size()};
        count = Arrays.stream(lengths).max().getAsInt();

        NormalDistribution normalDistribution = new NormalDistribution(1, 0.3);

        for (int i = 0; i < count; i++) {
            double xCoord = (double) i/((double) count/2);
            long childCount = Math.round(normalDistribution.density(xCoord) * 100);
            for (int j = 0; j < childCount; j++) {
                SCA current = factory.manufacturePojo(SCA.class);

                current.setCellAutomaton(cellAutomatons.get(i));
                current.setScience(sciences.get(i * sciences.size() / count));

                scas.add(current);
            }
        }

        scaRepository.saveAll(scas);

        return scas;
    }

    private List<Science> fillScience(int count) {
        PodamFactory factory = new PodamFactoryImpl();
        List<Science> sciences = new ArrayList<>(count);

        for (int i = 0; i < count; i++)
            sciences.add(factory.manufacturePojo(Science.class));

        scienceRepository.saveAll(sciences);

        return sciences;
    }

    private List<StartState> fillStartState(int count) {
        PodamFactory factory = new PodamFactoryImpl();
        List<StartState> startStates = new ArrayList<>(count);

        for (int i = 0; i < count; i++)
            startStates.add(factory.manufacturePojo(StartState.class));

        startStateRepository.saveAll(startStates);

        return startStates;
    }

    private List<Step> fillStep(int count) {
        PodamFactory factory = new PodamFactoryImpl();
        List<Step> steps = new ArrayList<>(count);

        for (int i = 0; i < count; i++)
            steps.add(factory.manufacturePojo(Step.class));

        stepRepository.saveAll(steps);

        return steps;
    }

    private List<Structure> fillStructure(int count) {
        PodamFactory factory = new PodamFactoryImpl();
        List<Structure> structures = new ArrayList<>(count);

        for (int i = 0; i < count; i++)
            structures.add(factory.manufacturePojo(Structure.class));

        structureRepository.saveAll(structures);

        return structures;
    }
    

//    private String generateRandomString() {
//        int length = 10;
//        boolean useLetters = true;
//        boolean useNumbers = true;
//        return RandomStringUtils.random(length, useLetters, useNumbers);
//    }
}
