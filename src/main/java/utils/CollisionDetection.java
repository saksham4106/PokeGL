package utils;

import game.Transform;

public class CollisionDetection {

    public static boolean pointInBox(float pointX, float pointY, float boxX, float boxY, float boxWidth, float boxHeight){
        return (pointX > boxX && pointX < boxX + boxWidth) &&
                (pointY > boxY && pointY < boxY + boxHeight);
    }

    public static boolean boxInBox(Transform box1, Transform box2){
        return boxInBox(box1.position.x, box1.position.y, box1.scale.x, box1.scale.y,
                box2.position.x, box2.position.y, box2.scale.x, box2.scale.y);
    }

    public static boolean boxInBox(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2){
        return x1 < x2 + w2 && x1 + w1 > x2 & y1 < y2 + h2 && h1 + y1 > y2;
    }
//
//    public static class QuadTree{
//        private int maxObjects = 10;
//        private int maxLevels = 5;
//
//        private int level;
//        private List<Transform> objects;
//        private Transform transform;
//        private QuadTree[] nodes;
//
//        public QuadTree(int level, Transform transform) {
//            this.level = level;
//            this.objects = new ArrayList<Transform>();
//            this.nodes = new QuadTree[4];
//            this.transform = transform;
//        }
//
//        public void clear(){
//            objects.clear();
//            for (QuadTree node : nodes) {
//                if(node != null) node.clear();
//            }
//        }
//
//        public void split(){
//            int subWidth = (int) this.transform.scale.x / 2;
//            int subHeight = (int) this.transform.scale.y / 2;
//            int x = (int) this.transform.position.x;
//            int y = (int) this.transform.position.y;
//
//            nodes[0] = new QuadTree(level + 1, new Transform(x + subWidth, y, subWidth, subHeight));
//            nodes[1] = new QuadTree(level+1, new Transform(x, y, subWidth, subHeight));
//            nodes[2] = new QuadTree(level+1, new Transform(x, y + subHeight, subWidth, subHeight));
//            nodes[3] = new QuadTree(level+1, new Transform(x + subWidth, y + subHeight, subWidth, subHeight));
//        }
//
//        private int getIndex(Transform transform){
//            int index = -1;
//            float x = this.transform.position.x;
//            float y = this.transform.position.y;
//            float w = this.transform.scale.x;
//            float h = this.transform.scale.y;
//            float x1 = transform.position.x;
//            float y1 = transform.position.y;
//            float w1 = transform.scale.x;
//            float h1 = transform.scale.y;
//            double verticalMidPoint = x + (w / 2);
//            double horizontalMidPoint = y + (h / 2);
//
//            boolean topQuadrant = y1 < horizontalMidPoint && y1 + h1 < horizontalMidPoint;
//            boolean bottomQuadrant = y1 > horizontalMidPoint;
//
//            if(x1 < verticalMidPoint && x1 + w1 < verticalMidPoint){
//                if(topQuadrant) index = 1;
//                else if(bottomQuadrant) index = 2;
//
//            }else if(x1 > verticalMidPoint){
//                if(topQuadrant) index = 0;
//                else if(bottomQuadrant) index = 3;
//            }
//
//            return index;
//        }
//
//        public void insert(Transform transform){
//            if(nodes[0] != null){
//                int index = getIndex(transform);
//
//                if(index != -1){
//                    nodes[index].insert(transform);
//                    return;
//                }
//            }
//
//            objects.add(transform);
//
//            if(objects.size() > maxObjects && level < maxLevels){
//                if(nodes[0] == null){
//                    split();
//                }
//
//                int i = 0;
//                while(i < objects.size()){
//                    int index = getIndex(objects.get(i));
//                    if(index != -1){
//                        nodes[index].insert(objects.remove(i));
//                    }else{
//                        i++;
//                    }
//                }
//            }
//        }
//
//        public List<Transform> retrieve(List<Transform> returnObjects, Transform transform){
//            int index = getIndex(transform);
//            if(index != -1 && nodes[0] != null){
//                nodes[index].retrieve(returnObjects, transform);
//            }
//
//            returnObjects.addAll(objects);
//            return returnObjects;
//        }
//    }
}
