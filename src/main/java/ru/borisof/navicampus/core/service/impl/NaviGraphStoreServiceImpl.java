//package ru.borisof.navicampus.core.service.impl;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Isolation;
//import org.springframework.transaction.annotation.Transactional;
//import ru.borisof.navicampus.core.api.model.GraphDataModel;
//import ru.borisof.navicampus.core.api.model.NodeModel;
//import ru.borisof.navicampus.core.dao.domain.Building;
//import ru.borisof.navicampus.core.dao.domain.GraphEdge;
//import ru.borisof.navicampus.core.dao.domain.GraphNode;
//import ru.borisof.navicampus.core.repo.GraphEdgeRepo;
//import ru.borisof.navicampus.core.repo.GraphNodeRepo;
//import ru.borisof.navicampus.core.service.BuildingService;
//import ru.borisof.navicampus.core.service.NaviGraphStoreService;
//
//import javax.validation.ValidationException;
//import java.util.List;
//import java.util.TreeMap;
//import java.util.UUID;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//@Service
//@RequiredArgsConstructor
//public class NaviGraphStoreServiceImpl implements NaviGraphStoreService {
//
//    private final GraphEdgeRepo edgeRepo;
//    private final GraphNodeRepo nodeRepo;
//    private final BuildingService buildingService;
//
//    @Override
//    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
//    public void saveGraphData(GraphDataModel graphData) {
//
//        Building currentBuilding = buildingService.getBuilding(graphData.buildingId());
//
//        if (currentBuilding.getFloorCount() < graphData.floor()) {
//            throw new ValidationException("Некорректно задан этаж здания");
//        }
//
//        clearCurrentFloorData(currentBuilding.getId(), graphData.floor());
//        saveGraphData(graphData, currentBuilding);
//    }
//
//    void saveGraphData(GraphDataModel graphData, Building building) {
//        //Конвертируем модель в мапу доменов, готовых для сохранения.
//        // В качестве ключа используется число (индекс, который был передан нам для фронта,
//        // он используется для связи конкретной ноды с гранью графа.
//        // Использовать только TreeMap, поскольку нужна минимальная алгоритмическая сложность поиска\вставки (O(log n))
//        TreeMap<Long, GraphNode> nodeTreeMap = graphData.nodeList().stream()
//                .collect(Collectors.toMap(
//                        NodeModel::index,
//                        (node) -> toEntity(node, building, graphData.floor()),
//                        (gn1, gn2) -> gn1,
//                        TreeMap::new));
//
//        // Конвертируем теперь грани графа. Примечательно то, что мы делаем две связи, чтобы маршрут был биполярным
//        var l = graphData.edgeModelList().stream()
//                .flatMap(eg -> linkAndPrepareEdgePair(
//                        nodeTreeMap.get(eg.startIndex()),
//                        nodeTreeMap.get(eg.endIndex()))
//                        .stream())
//                .collect(Collectors.toList());
//
//        edgeRepo.saveAll(l);
//
//    }
//
//    private List<GraphEdge> linkAndPrepareEdgePair(GraphNode gn1, GraphNode gn2) {
//
//        //Здесь лайфFUCK: чтобы два раза не вызывать код просто меняя местами две ноды, делаем в цикле от 0 до 1,
//        // На основе перебираемого значения выбирается место для ноды (прямой или обратный порядок)
//        return IntStream.range(0, 2)
//                .mapToObj((idx) -> GraphEdge.builder()
//                        .id(UUID.randomUUID())
//                        .firstNode(idx == 0 ? gn1 : gn2)
//                        .secondNode(idx != 0 ? gn1 : gn2)
//                        .weight(1.)
//                        .build())
//                .collect(Collectors.toList());
//    }
//
//    private GraphNode toEntity(NodeModel nodeModel, Building building, int floor) {
//        return GraphNode.builder()
//                .id(UUID.randomUUID())
//                .building(building)
//                .floor(floor)
//                .x(nodeModel.xPosition())
//                .y(nodeModel.yPosition())
//                .build();
//    }
//
//    // В рамках одной транзакции чистим все существующие данные о графе в БД для дальнейшего заполнения
//    // Поскольку с нодами графа еще тянутся и связи графа, то на уровне сущностей сделан каскад, чтобы при удалении ноды
//    // дропались и связи, связанные с ней
//    private void clearCurrentFloorData(int buildingId, int floor) {
//        nodeRepo.deleteByBuildingIdAndFloor(buildingId, floor);
//    }
//
//}
