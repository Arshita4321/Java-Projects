import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Graphics2D;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DigitalArtCanvas  extends JFrame {
    DrawArea drawArea ;
    JButton clearButton, saveButton,undoButton;
    JSlider brushSlider ;
    JComboBox<String> colorPiceker;
    JToggleButton eraseButton;
    JPanel colorPreview;


    DigitalArtCanvas() {
        setTitle("Digital Art Canvas");
        setSize(800,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Initialize the custom drawing panel
        drawArea = new DrawArea();
        add(drawArea, BorderLayout.CENTER);

        //panel for holding buttons and controls
        JPanel controls = new JPanel();
        clearButton = new JButton("Clear");
        clearButton.addActionListener(e-> drawArea.clearCanvas());
        controls.add(clearButton);

        saveButton = new JButton("Save");
        saveButton.addActionListener(e->drawArea.saveImage());
        controls.add(saveButton);

         undoButton = new JButton("Undo");
         undoButton.addActionListener(e-> drawArea.undoLastState());
        controls.add(undoButton);



//        colorPiceker = new JComboBox<>(new String[]{"Black", "Red", "Green", "Blue", "Pink","Orange"});
//        colorPiceker.addActionListener(e-> drawArea.setColor(colorPiceker.getSelectedItem().toString()));
//        controls.add(new JLabel("Color:  "));
//        controls.add(colorPiceker);

        JButton pickColorButton = new JButton("Choose color");
        pickColorButton.addActionListener(e-> {
            Color selectedColor = JColorChooser.showDialog(null, "Choose Custom Color", Color.BLACK);
            if(selectedColor != null){
                drawArea.setCustomBrushColor(selectedColor);
                colorPreview.setBackground(selectedColor);
            }
        });
        controls.add(pickColorButton);

        brushSlider = new JSlider(1,20,5);
        brushSlider.addChangeListener(e -> drawArea.setBrushSize(brushSlider.getValue()));
        controls.add(new JLabel("Brush Size: "));;
        controls.add(brushSlider);

        eraseButton = new JToggleButton(("Erase"));
        eraseButton.addActionListener(e-> drawArea.setEraserMode(eraseButton.isSelected()));
        controls.add(eraseButton);

        colorPreview = new JPanel();
        colorPreview.setPreferredSize(new Dimension(20,20));
        colorPreview.setBackground(Color.BLACK);
        controls.add(colorPreview);

        JLabel status = new JLabel("X: 0 Y: 0");
            add(status, BorderLayout.NORTH);
        drawArea.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                status.setText("X: " + e.getX() + " Y: " + e.getY());
            }
        });

        add(controls,BorderLayout.SOUTH);

        setVisible(true);
    }

    class DrawArea extends JPanel{
        Image image;
        Graphics2D g2;
        int prevX,prevY;
        Color  brushColor = Color.BLACK;
        BufferedImage previousState;
        int brushSize = 5;

        boolean eraseMode = false;

        DrawArea() {

            setDoubleBuffered(false);   // help in real time

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    prevX = e.getX();
                    prevY = e.getY();
                    saveCurrentState();
                }
            });
            addMouseMotionListener (new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    int x = e.getX();
                    int y = e.getY();
                    if(g2 != null) {
                        //g2.setColor(brushColor);;
                        g2.setStroke(new BasicStroke(brushSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                        g2.setColor(eraseMode ? Color.WHITE : brushColor);
                        g2.drawLine(prevX,prevY,x,y);
                        repaint();

                        prevX = x;
                        prevY = y;
                    }
                }
            });
        }
        protected void paintComponent(Graphics g){
            if(image == null){
               image =  createImage(getSize().width, getSize().height);
               //image.getGraphics().drawImage(image,0,0,this);
               g2 = (Graphics2D) image.getGraphics();
               g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
               clearCanvas();
            }
            g.drawImage(image,0,0,this);
        }

        void clearCanvas(){
            g2.setPaint(Color.WHITE);
            g2.fillRect(0,0,getSize().width,getSize().height);
            g2.setPaint(brushColor);
            repaint();
        }

        void setColor(String colorName){
            switch(colorName){
                case "Red":
                    brushColor = Color.RED;
                    break;
                case "Green":
                    brushColor = Color.GREEN;
                    break;
               case "Blue":
                   brushColor = Color.BLUE;
                   break;
               case "Pink":
                   brushColor = Color.PINK;
                   break;
               case "Orange":
                   brushColor = Color.ORANGE;
                   break;
               case "Black":
                   brushColor = Color.BLACK;
                   break;
               default:
                   brushColor = Color.BLACK;
            }

        }
        void saveImage(){
            try {
                BufferedImage bi =  new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = bi.createGraphics();
                paint(g2d);
                ImageIO.write(bi, "PNG", new File("Output.png"));
                JOptionPane.showMessageDialog(null, "Image Saved Successfully.....");
            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }

        }

        void saveCurrentState(){
            if(image != null){
                previousState = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = previousState.createGraphics();
                g.drawImage(image,0,0,null);
                g.dispose();
            }
        }

        void undoLastState(){
            if (previousState != null && image != null) {
                Graphics2D g = (Graphics2D) image.getGraphics();
                g.drawImage(previousState, 0, 0, null);
                g.dispose();
                repaint();
            }

        }
        void setCustomBrushColor(Color colorName){
            brushColor = colorName;

        }
        //change brush size
        public void setBrushSize(int brushSize) {
            this.brushSize = brushSize;
        }

        void setEraserMode(boolean mode){
            eraseMode = mode;
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(DigitalArtCanvas::new);
    }
}
