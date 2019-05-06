import java.awt.Graphics;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

class ImageLabel extends JLabel {

    public ImageLabel(String img) {
      ImageIcon icon = createImageIcon(img, "hey");
      setIcon(icon);
      // setMargin(new Insets(0,0,0,0));
      setIconTextGap(0);
      // setBorderPainted(false);
      setBorder(null);
      setText(null);
      setSize(icon.getImage().getWidth(null), icon.getImage().getHeight(null));
    }
  
    public ImageLabel(ImageIcon icon) {
      setIcon(icon);
      // setMargin(new Insets(0,0,0,0));
      setIconTextGap(0);
      // setBorderPainted(false);
      setBorder(null);
      setText(null);
      setSize(icon.getImage().getWidth(null), icon.getImage().getHeight(null));
    }

    protected ImageIcon createImageIcon(String path, String description) {
      java.net.URL imgURL = getClass().getResource(path);
      if (imgURL != null) {
          return new ImageIcon(imgURL, description);
      } else {
          System.err.println("Couldn't find file: " + path);
          return null;
      }
    }

    public void paint(Graphics page)
    {
      Point p = this.getLocation();
      getIcon().paintIcon(Project.tm, page, p.x, p.y);
    }
  }