begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003  Nizar N. Batada, Morten O. Alver  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
end_comment

begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionListener
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JToolBar
import|;
end_import

begin_class
DECL|class|SidePaneHeader
specifier|public
class|class
name|SidePaneHeader
extends|extends
name|JPanel
implements|implements
name|ActionListener
block|{
DECL|field|close
specifier|private
name|JButton
name|close
init|=
operator|new
name|JButton
argument_list|(
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|close2IconFile
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|nameLabel
DECL|field|imageIcon
specifier|private
name|JLabel
name|nameLabel
decl_stmt|,
name|imageIcon
decl_stmt|;
DECL|field|parent
specifier|private
name|SidePaneComponent
name|parent
decl_stmt|;
DECL|field|gbl
specifier|private
name|GridBagLayout
name|gbl
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
DECL|field|con
specifier|private
name|GridBagConstraints
name|con
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
comment|/*     public SidePaneHeader(String name, URL image, JButton button, 			  JComponent parent_) {  			  }*/
DECL|method|SidePaneHeader (String name, URL image, SidePaneComponent parent_)
specifier|public
name|SidePaneHeader
parameter_list|(
name|String
name|name
parameter_list|,
name|URL
name|image
parameter_list|,
name|SidePaneComponent
name|parent_
parameter_list|)
block|{
name|addPart
argument_list|(
name|name
argument_list|,
name|image
argument_list|,
name|parent_
argument_list|)
expr_stmt|;
block|}
DECL|method|paintComponent (Graphics g)
specifier|public
name|void
name|paintComponent
parameter_list|(
name|Graphics
name|g
parameter_list|)
block|{
name|Graphics2D
name|g2
init|=
operator|(
name|Graphics2D
operator|)
name|g
decl_stmt|;
name|Paint
name|oldPaint
init|=
name|g2
operator|.
name|getPaint
argument_list|()
decl_stmt|;
comment|//g2.setColor(Color.red);
name|Insets
name|ins
init|=
name|getInsets
argument_list|()
decl_stmt|;
name|int
name|width
init|=
name|getWidth
argument_list|()
operator|-
name|ins
operator|.
name|left
operator|-
name|ins
operator|.
name|right
decl_stmt|,
name|height
init|=
name|getHeight
argument_list|()
operator|-
name|ins
operator|.
name|top
operator|-
name|ins
operator|.
name|bottom
decl_stmt|;
comment|//g2.setPaint(new GradientPaint(0, 0, GUIGlobals.gradientGray,
comment|//                              width, height, GUIGlobals.gradientBlue, false));
name|g2
operator|.
name|setPaint
argument_list|(
operator|new
name|GradientPaint
argument_list|(
name|ins
operator|.
name|left
argument_list|,
name|ins
operator|.
name|top
argument_list|,
name|GUIGlobals
operator|.
name|gradientGray
argument_list|,
name|width
argument_list|,
name|height
argument_list|,
name|GUIGlobals
operator|.
name|gradientBlue
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|g2
operator|.
name|fillRect
argument_list|(
name|ins
operator|.
name|left
argument_list|,
name|ins
operator|.
name|top
argument_list|,
name|width
operator|-
literal|1
argument_list|,
name|height
argument_list|)
expr_stmt|;
comment|//g2.fillRect(0, 0, 100, 10);
name|g2
operator|.
name|setPaint
argument_list|(
name|oldPaint
argument_list|)
expr_stmt|;
comment|//super.paintComponent(g);
block|}
comment|//public boolean isOpaque() { return true; }
DECL|method|addPart (String name, URL image, SidePaneComponent parent_)
specifier|private
name|void
name|addPart
parameter_list|(
name|String
name|name
parameter_list|,
name|URL
name|image
parameter_list|,
name|SidePaneComponent
name|parent_
parameter_list|)
block|{
name|parent
operator|=
name|parent_
expr_stmt|;
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
comment|//setPreferredSize(new Dimension(GUIGlobals.SPLIT_PANE_DIVIDER_LOCATION, 18));
comment|//setMinimumSize(new Dimension(GUIGlobals.SPLIT_PANE_DIVIDER_LOCATION, 18));
comment|//imageIcon = new JLabel(new ImageIcon(image));
name|nameLabel
operator|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
name|name
argument_list|)
argument_list|,
operator|new
name|ImageIcon
argument_list|(
name|image
argument_list|)
argument_list|,
name|SwingConstants
operator|.
name|LEFT
argument_list|)
expr_stmt|;
comment|//        setBackground(new Color(0, 0, 175)); //SystemColor.activeCaption);
comment|//close.setOpaque(false);
name|nameLabel
operator|.
name|setForeground
argument_list|(
operator|new
name|Color
argument_list|(
literal|230
argument_list|,
literal|230
argument_list|,
literal|230
argument_list|)
argument_list|)
expr_stmt|;
comment|//nameLabel.setPreferredSize(new Dimension(70, 24));
comment|/*AbstractAction close = new AbstractAction("Close", new ImageIcon(GUIGlobals.closeIconFile)) {           public void actionPerformed(ActionEvent e) {             parent.hideAway();           }         }; 	close.putValue(close.SHORT_DESCRIPTION, "Close");         JToolBar tlb = new JToolBar();         tlb.setFloatable(false);         tlb.setMargin(new Insets(0,0,0,0));         tlb.setSize(20, 20);         tlb.add(close);*/
comment|//close.setMargin(new Insets(0,0,0,0));
comment|//close.setRolloverEnabled(true);
name|close
operator|.
name|setBorder
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|close
operator|.
name|setOpaque
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|close
operator|.
name|setPreferredSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|15
argument_list|,
literal|15
argument_list|)
argument_list|)
expr_stmt|;
name|close
operator|.
name|setMaximumSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|15
argument_list|,
literal|15
argument_list|)
argument_list|)
expr_stmt|;
name|close
operator|.
name|setMinimumSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|15
argument_list|,
literal|15
argument_list|)
argument_list|)
expr_stmt|;
name|close
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
comment|//setBorder(BorderFactory.createEtchedBorder());
comment|//setBorder(BorderFactory.createMatteBorder(1,1,1,2,new Color(150,150,150)));
comment|//add(imageIcon, BorderLayout.WEST);
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|WEST
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|NONE
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|nameLabel
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|nameLabel
argument_list|)
expr_stmt|;
name|JPanel
name|pan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|pan
operator|.
name|setOpaque
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|HORIZONTAL
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|pan
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|pan
argument_list|)
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|NONE
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|close
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|close
argument_list|)
expr_stmt|;
block|}
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|parent
operator|.
name|hideAway
argument_list|()
expr_stmt|;
comment|//setVisible(false);
block|}
block|}
end_class

end_unit

