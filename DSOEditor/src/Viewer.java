import de.sunnix.sdso.DataSaveObject;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class Viewer extends JFrame {

    private JTree tree;
    private DefaultMutableTreeNode root;

    private File latestFile;

    public Viewer(){
        var content = new JPanel(new BorderLayout());
        content.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        setContentPane(content);
        setJMenuBar(createMenu());

        content.add(createTree(), BorderLayout.CENTER);

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JMenuBar createMenu() {
        var menu = new JMenuBar();

        var menu_file = new JMenu("File");
        var openFile = new JMenuItem("Open");
        openFile.addActionListener(this::openFile);
        menu_file.add(openFile);
        var exit = new JMenuItem("Exit");
        exit.addActionListener(l -> dispose());
        menu_file.add(exit);
        menu.add(menu_file);

        return menu;
    }

    private void openFile(ActionEvent actionEvent) {
        var chooser = new JFileChooser(latestFile);
        if(chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
            return;
        latestFile = chooser.getSelectedFile();
        try (var stream = new FileInputStream(latestFile)){
            root.removeAllChildren();
            genChilds(new DataSaveObject(stream), root);
            ((DefaultTreeModel)tree.getModel()).reload();
        } catch (Exception e){
            root.removeAllChildren();
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading file (" + e.getMessage() + ")", "Load file error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void genChilds(DataSaveObject dso, DefaultMutableTreeNode parent){
        var data = dso.getData();
        for(var d: data){
            var node = new DefaultMutableTreeNode();
            var name = d.getKey();
            var value = d.getValue();
            if(value instanceof Byte)
                name += ": " + value + " (By)";
            else if(value instanceof Short)
                name += ": " + value + " (Sh)";
            else if(value instanceof Integer)
                name += ": " + value + " (In)";
            else if(value instanceof Long)
                name += ": " + value + " (Lo)";
            else if(value instanceof Float)
                name += ": " + value + " (Fl)";
            else if(value instanceof Double)
                name += ": " + value + " (Do)";
            else if(value instanceof Boolean)
                name += ": " + value + " (Bo)";
            else if(value instanceof Character)
                name += ": " + value + " (Ch)";
            else if(value instanceof String)
                name += ": " + value + " (St)";
            else {
                if(value instanceof DataSaveObject cDSO)
                    genChilds(cDSO, node);
                else if (value instanceof DataSaveObject.DataSaveArray<?> arr) {
                    name += " (Array)";
                    try {
                        var field = arr.getClass().getDeclaredField("list");
                        field.setAccessible(true);
                        var list = (List<?>) field.get(value);
                        genChilds(list, node);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else if (value instanceof byte[] arr){
                    name += " (Data)";
                    for(var i = 0; i < arr.length; i++)
                        node.add(new DefaultMutableTreeNode(i + ": " + arr[i] + " (By)"));
                } else {
                    System.err.println(name + " is a invalid datatype!");
                    continue;
                }
            }
            node.setUserObject(name);
            parent.add(node);
        }
    }

    private void genChilds(List<?> list, DefaultMutableTreeNode parent){
        if(list.isEmpty())
            return;
        var value = list.get(0);
        var typename = "";
        if(value instanceof Byte)
            typename = " (By)";
        else if(value instanceof Short)
            typename = " (Sh)";
        else if(value instanceof Integer)
            typename = " (In)";
        else if(value instanceof Long)
            typename = " (Lo)";
        else if(value instanceof Float)
            typename = " (Fl)";
        else if(value instanceof Double)
            typename = " (Do)";
        else if(value instanceof Boolean)
            typename = " (Bo)";
        else if(value instanceof Character)
            typename = " (Ch)";
        else if(value instanceof String)
            typename = " (St)";
        else if(value instanceof DataSaveObject) {
            for(var i = 0; i < list.size(); i++){
                var node = new DefaultMutableTreeNode(i);
                genChilds((DataSaveObject) list.get(i), node);
                parent.add(node);
            }
            return;
        } else if(value instanceof byte[]){
            typename = " (Data)";
            for(var i = 0; i < list.size(); i++) {
                var node = new DefaultMutableTreeNode(i + typename);
                var arr = (byte[]) list.get(i);
                for(var j = 0; j < arr.length; j++)
                    node.add(new DefaultMutableTreeNode(i + ": " + arr[i] + " (By)"));
                parent.add(node);
            }
            return;
        } else {
            System.err.println(parent.getUserObject() + " has a invalid datatype array!");
            return;
        }
        for(var i = 0; i < list.size(); i++)
            parent.add(new DefaultMutableTreeNode(i + ": " + list.get(i) + typename));
    }

    private JScrollPane createTree() {
        tree = new JTree();
        root = new DefaultMutableTreeNode("root");
        tree.setModel(new DefaultTreeModel(root));
        tree.setRootVisible(false);
        var scroll = new JScrollPane(tree);
        scroll.setPreferredSize(new Dimension(400, 600));
        return scroll;
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        new Viewer();
    }

}
