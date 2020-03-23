package Frames;

import FileSearcher.FileAnalyser;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JTreeEntity implements TreeModel {

    private String root;
    private List<String> nodeArrayList = new ArrayList<>();

    public JTreeEntity(String root, List<File> fileCollection){
        this.root = root;

        for (File entity:fileCollection){
            String relativePath = entity.toString().replace(root + "/","");
            String nodePath = root;

            for(String node:relativePath.split("/")){
                nodePath = nodePath + "/" + node;
                if(!nodeArrayList.contains(nodePath)){
                    nodeArrayList.add(nodePath);
                }
            }
        }
    }

    private List<String> getChildCollection(String parrent)
    {
        ArrayList<String> arrayList = new ArrayList<>();

        for (String entity:nodeArrayList){
            if(entity.startsWith(parrent) && (entity.split("/").length - parrent.split("/").length)==1){
                arrayList.add(entity);
            }
        }

        return arrayList;
    }

    @Override
    public Object getRoot() {
        return root;
    }

    @Override
    public Object getChild(Object parent, int index) {
        List<String> collection = getChildCollection(parent.toString());
        return collection.get(index);
    }

    @Override
    public int getChildCount(Object parent) {
        List<String> collection = getChildCollection(parent.toString());
        return collection.size();
    }

    @Override
    public boolean isLeaf(Object node) {
        List<String> collection = getChildCollection(node.toString());
        return collection.size() == 0;
    }

    //Needn't this one
    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {

    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        List<String> collection = getChildCollection(parent.toString());
        if(collection.contains(child.toString())){
            return 1;
        }
        else {
            return -1;
        }
    }
    //Needn't this one
    @Override
    public void addTreeModelListener(TreeModelListener l) {}
    //Needn't this one
    @Override
    public void removeTreeModelListener(TreeModelListener l) {}
}
