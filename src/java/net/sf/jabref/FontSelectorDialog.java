begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*   Taken from JpicEdt modified slightly by nizar batada for JabRef     EepicViewFactory.java - February 11, 2002 - jPicEdt, a picture editor for LaTeX.  copyright (C) 1999-2002 Sylvain Reynal  Portions copyright (C) 2000, 2001 Slava Pestov  Portions copyright (C) 1999 Jason Ginchereau   Département de Physique  Ecole Nationale Supérieure de l'Electronique et de ses Applications (ENSEA)  6, avenue du Ponceau  F-95014 CERGY CEDEX   Tel : +33 130 736 245  Fax : +33 130 736 667  e-mail : reynal@ensea.fr  jPicEdt web page : http://trashx.ensea.fr/jpicedt/     This program is free software; you can redistribute it and/or  modify it under the terms of the GNU General Public License  as published by the Free Software Foundation; either version 2  of the License, or any later version.     This program is distributed in the hope that it will be useful,  but WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  GNU General Public License for more details.     You should have received a copy of the GNU General Public License  along with this program; if not, write to the Free Software  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.  */
end_comment

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
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
name|util
operator|.
name|Vector
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|border
operator|.
name|*
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|*
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|*
import|;
end_import

begin_comment
comment|/**  * A font chooser widget.  * @author Slava Pestov (jEdit), Sylvain Reynal  * @since jpicedt 1.3.2.beta-9  * @version $Id$  *<p>  * $Log$  * Revision 1.1  2003/11/07 22:18:07  nbatada  * modified it slightly from initial version  *  * Revision 1.1  2003/11/07 22:14:34  nbatada  * modified it from initial version  *  * Revision 1.4  2003/11/02 01:51:06  reynal  * Cleaned-up i18n labels  *  * Revision 1.3  2003/08/31 22:05:40  reynal  *  * Enhanced class interface for some widgets.  *    */
end_comment

begin_class
DECL|class|FontSelector
class|class
name|FontSelector
extends|extends
name|JButton
block|{
DECL|field|PLAIN
specifier|static
specifier|final
name|String
name|PLAIN
init|=
literal|"plain"
decl_stmt|;
DECL|field|BOLD
specifier|static
specifier|final
name|String
name|BOLD
init|=
literal|"bold"
decl_stmt|;
DECL|field|BOLD_ITALIC
specifier|static
specifier|final
name|String
name|BOLD_ITALIC
init|=
literal|"bold-italic"
decl_stmt|;
DECL|field|ITALIC
specifier|static
specifier|final
name|String
name|ITALIC
init|=
literal|"italic"
decl_stmt|;
comment|/** init with a default font */
DECL|method|FontSelector ()
specifier|public
name|FontSelector
parameter_list|()
block|{
name|this
argument_list|(
operator|new
name|Font
argument_list|(
literal|"SansSerif"
argument_list|,
name|Font
operator|.
name|PLAIN
argument_list|,
literal|10
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/** init with the given font */
DECL|method|FontSelector (Font font)
specifier|public
name|FontSelector
parameter_list|(
name|Font
name|font
parameter_list|)
block|{
name|setFont
argument_list|(
name|font
argument_list|)
expr_stmt|;
name|setRequestFocusEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|addActionListener
argument_list|(
operator|new
name|ActionHandler
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|setFont (Font font)
specifier|public
name|void
name|setFont
parameter_list|(
name|Font
name|font
parameter_list|)
block|{
name|super
operator|.
name|setFont
argument_list|(
name|font
argument_list|)
expr_stmt|;
name|updateText
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * update button's text content from the current button's font. 	 */
DECL|method|updateText ()
specifier|private
name|void
name|updateText
parameter_list|()
block|{
name|Font
name|font
init|=
name|getFont
argument_list|()
decl_stmt|;
name|String
name|styleString
decl_stmt|;
switch|switch
condition|(
name|font
operator|.
name|getStyle
argument_list|()
condition|)
block|{
case|case
name|Font
operator|.
name|PLAIN
case|:
name|styleString
operator|=
name|PLAIN
expr_stmt|;
break|break;
case|case
name|Font
operator|.
name|BOLD
case|:
name|styleString
operator|=
name|BOLD
expr_stmt|;
break|break;
case|case
name|Font
operator|.
name|ITALIC
case|:
name|styleString
operator|=
name|ITALIC
expr_stmt|;
break|break;
case|case
name|Font
operator|.
name|BOLD
operator||
name|Font
operator|.
name|ITALIC
case|:
name|styleString
operator|=
name|BOLD_ITALIC
expr_stmt|;
break|break;
default|default:
name|styleString
operator|=
literal|"UNKNOWN!!!???"
expr_stmt|;
break|break;
block|}
name|setText
argument_list|(
name|font
operator|.
name|getFamily
argument_list|()
operator|+
literal|" "
operator|+
name|font
operator|.
name|getSize
argument_list|()
operator|+
literal|" "
operator|+
name|styleString
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * button's action-listener ; open a FontSelectorDialog 	 */
DECL|class|ActionHandler
class|class
name|ActionHandler
implements|implements
name|ActionListener
block|{
DECL|method|actionPerformed (ActionEvent evt)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|evt
parameter_list|)
block|{
name|Font
name|font
init|=
operator|new
name|FontSelectorDialog
argument_list|(
name|FontSelector
operator|.
name|this
argument_list|,
name|getFont
argument_list|()
argument_list|)
operator|.
name|getSelectedFont
argument_list|()
decl_stmt|;
if|if
condition|(
name|font
operator|!=
literal|null
condition|)
block|{
name|setFont
argument_list|(
name|font
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

begin_comment
comment|///////////////////////////////////////////////////////////////////////////////
end_comment

begin_comment
comment|/**  *  */
end_comment

begin_class
DECL|class|FontSelectorDialog
specifier|public
class|class
name|FontSelectorDialog
extends|extends
name|JDialog
block|{
comment|/** 	 * 	 */
DECL|field|PLAIN
specifier|static
specifier|final
name|String
name|PLAIN
init|=
literal|"plain"
decl_stmt|;
DECL|field|BOLD
specifier|static
specifier|final
name|String
name|BOLD
init|=
literal|"bold"
decl_stmt|;
DECL|field|BOLD_ITALIC
specifier|static
specifier|final
name|String
name|BOLD_ITALIC
init|=
literal|"bold-italic"
decl_stmt|;
DECL|field|ITALIC
specifier|static
specifier|final
name|String
name|ITALIC
init|=
literal|"italic"
decl_stmt|;
DECL|method|FontSelectorDialog (Component comp, Font font)
specifier|public
name|FontSelectorDialog
parameter_list|(
name|Component
name|comp
parameter_list|,
name|Font
name|font
parameter_list|)
block|{
comment|//super(JOptionPane.getFrameForComponent(comp),jpicedt.Localizer.currentLocalizer().get("widget.FontSelector"),true); //
name|super
argument_list|(
name|JOptionPane
operator|.
name|getFrameForComponent
argument_list|(
name|comp
argument_list|)
argument_list|,
literal|"FontSelector"
argument_list|,
literal|true
argument_list|)
expr_stmt|;
comment|//
name|JPanel
name|content
init|=
operator|new
name|JPanel
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
decl_stmt|;
name|content
operator|.
name|setBorder
argument_list|(
operator|new
name|EmptyBorder
argument_list|(
literal|12
argument_list|,
literal|12
argument_list|,
literal|12
argument_list|,
literal|12
argument_list|)
argument_list|)
expr_stmt|;
name|setContentPane
argument_list|(
name|content
argument_list|)
expr_stmt|;
name|JPanel
name|listPanel
init|=
operator|new
name|JPanel
argument_list|(
operator|new
name|GridLayout
argument_list|(
literal|1
argument_list|,
literal|3
argument_list|,
literal|6
argument_list|,
literal|6
argument_list|)
argument_list|)
decl_stmt|;
name|JPanel
name|familyPanel
init|=
name|createTextFieldAndListPanel
argument_list|(
literal|"Font Family"
argument_list|,
name|familyField
operator|=
operator|new
name|JTextField
argument_list|()
argument_list|,
name|familyList
operator|=
operator|new
name|JList
argument_list|(
name|getFontList
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|listPanel
operator|.
name|add
argument_list|(
name|familyPanel
argument_list|)
expr_stmt|;
name|String
index|[]
name|sizes
init|=
block|{
literal|"9"
block|,
literal|"10"
block|,
literal|"12"
block|,
literal|"14"
block|,
literal|"16"
block|,
literal|"18"
block|,
literal|"24"
block|}
decl_stmt|;
name|JPanel
name|sizePanel
init|=
name|createTextFieldAndListPanel
argument_list|(
literal|"Font Size"
argument_list|,
name|sizeField
operator|=
operator|new
name|JTextField
argument_list|()
argument_list|,
name|sizeList
operator|=
operator|new
name|JList
argument_list|(
name|sizes
argument_list|)
argument_list|)
decl_stmt|;
name|listPanel
operator|.
name|add
argument_list|(
name|sizePanel
argument_list|)
expr_stmt|;
name|String
index|[]
name|styles
init|=
block|{
name|PLAIN
block|,
name|BOLD
block|,
name|ITALIC
block|,
name|BOLD_ITALIC
block|}
decl_stmt|;
name|JPanel
name|stylePanel
init|=
name|createTextFieldAndListPanel
argument_list|(
literal|"Font Style"
argument_list|,
name|styleField
operator|=
operator|new
name|JTextField
argument_list|()
argument_list|,
name|styleList
operator|=
operator|new
name|JList
argument_list|(
name|styles
argument_list|)
argument_list|)
decl_stmt|;
name|styleField
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|listPanel
operator|.
name|add
argument_list|(
name|stylePanel
argument_list|)
expr_stmt|;
name|familyList
operator|.
name|setSelectedValue
argument_list|(
name|font
operator|.
name|getFamily
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|familyField
operator|.
name|setText
argument_list|(
name|font
operator|.
name|getFamily
argument_list|()
argument_list|)
expr_stmt|;
name|sizeList
operator|.
name|setSelectedValue
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|font
operator|.
name|getSize
argument_list|()
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|sizeField
operator|.
name|setText
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|font
operator|.
name|getSize
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|styleList
operator|.
name|setSelectedIndex
argument_list|(
name|font
operator|.
name|getStyle
argument_list|()
argument_list|)
expr_stmt|;
name|styleField
operator|.
name|setText
argument_list|(
operator|(
name|String
operator|)
name|styleList
operator|.
name|getSelectedValue
argument_list|()
argument_list|)
expr_stmt|;
name|ListHandler
name|listHandler
init|=
operator|new
name|ListHandler
argument_list|()
decl_stmt|;
name|familyList
operator|.
name|addListSelectionListener
argument_list|(
name|listHandler
argument_list|)
expr_stmt|;
name|sizeList
operator|.
name|addListSelectionListener
argument_list|(
name|listHandler
argument_list|)
expr_stmt|;
name|styleList
operator|.
name|addListSelectionListener
argument_list|(
name|listHandler
argument_list|)
expr_stmt|;
name|content
operator|.
name|add
argument_list|(
name|BorderLayout
operator|.
name|NORTH
argument_list|,
name|listPanel
argument_list|)
expr_stmt|;
name|preview
operator|=
operator|new
name|JLabel
argument_list|(
literal|"Font Preview"
argument_list|)
expr_stmt|;
name|preview
operator|.
name|setBorder
argument_list|(
operator|new
name|TitledBorder
argument_list|(
literal|"Font Preview"
argument_list|)
argument_list|)
expr_stmt|;
name|updatePreview
argument_list|()
expr_stmt|;
name|Dimension
name|prefSize
init|=
name|preview
operator|.
name|getPreferredSize
argument_list|()
decl_stmt|;
name|prefSize
operator|.
name|height
operator|=
literal|50
expr_stmt|;
name|preview
operator|.
name|setPreferredSize
argument_list|(
name|prefSize
argument_list|)
expr_stmt|;
name|content
operator|.
name|add
argument_list|(
name|BorderLayout
operator|.
name|CENTER
argument_list|,
name|preview
argument_list|)
expr_stmt|;
name|JPanel
name|buttons
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|buttons
operator|.
name|setLayout
argument_list|(
operator|new
name|BoxLayout
argument_list|(
name|buttons
argument_list|,
name|BoxLayout
operator|.
name|X_AXIS
argument_list|)
argument_list|)
expr_stmt|;
name|buttons
operator|.
name|setBorder
argument_list|(
operator|new
name|EmptyBorder
argument_list|(
literal|12
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|buttons
operator|.
name|add
argument_list|(
name|Box
operator|.
name|createGlue
argument_list|()
argument_list|)
expr_stmt|;
name|ok
operator|=
operator|new
name|JButton
argument_list|(
literal|"OK"
argument_list|)
expr_stmt|;
name|ok
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionHandler
argument_list|()
argument_list|)
expr_stmt|;
name|getRootPane
argument_list|()
operator|.
name|setDefaultButton
argument_list|(
name|ok
argument_list|)
expr_stmt|;
name|buttons
operator|.
name|add
argument_list|(
name|ok
argument_list|)
expr_stmt|;
name|buttons
operator|.
name|add
argument_list|(
name|Box
operator|.
name|createHorizontalStrut
argument_list|(
literal|6
argument_list|)
argument_list|)
expr_stmt|;
name|cancel
operator|=
operator|new
name|JButton
argument_list|(
literal|"Cancel"
argument_list|)
expr_stmt|;
name|cancel
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionHandler
argument_list|()
argument_list|)
expr_stmt|;
name|buttons
operator|.
name|add
argument_list|(
name|cancel
argument_list|)
expr_stmt|;
name|buttons
operator|.
name|add
argument_list|(
name|Box
operator|.
name|createGlue
argument_list|()
argument_list|)
expr_stmt|;
name|content
operator|.
name|add
argument_list|(
name|BorderLayout
operator|.
name|SOUTH
argument_list|,
name|buttons
argument_list|)
expr_stmt|;
name|pack
argument_list|()
expr_stmt|;
name|setLocationRelativeTo
argument_list|(
name|JOptionPane
operator|.
name|getFrameForComponent
argument_list|(
name|comp
argument_list|)
argument_list|)
expr_stmt|;
name|show
argument_list|()
expr_stmt|;
block|}
DECL|method|ok ()
specifier|public
name|void
name|ok
parameter_list|()
block|{
name|isOK
operator|=
literal|true
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
DECL|method|cancel ()
specifier|public
name|void
name|cancel
parameter_list|()
block|{
name|dispose
argument_list|()
expr_stmt|;
block|}
DECL|method|getSelectedFont ()
specifier|public
name|Font
name|getSelectedFont
parameter_list|()
block|{
if|if
condition|(
operator|!
name|isOK
condition|)
return|return
literal|null
return|;
name|int
name|size
decl_stmt|;
try|try
block|{
name|size
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|sizeField
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|size
operator|=
literal|14
expr_stmt|;
block|}
return|return
operator|new
name|Font
argument_list|(
name|familyField
operator|.
name|getText
argument_list|()
argument_list|,
name|styleList
operator|.
name|getSelectedIndex
argument_list|()
argument_list|,
name|size
argument_list|)
return|;
block|}
comment|// private members
DECL|field|isOK
specifier|private
name|boolean
name|isOK
decl_stmt|;
DECL|field|familyField
specifier|private
name|JTextField
name|familyField
decl_stmt|;
DECL|field|familyList
specifier|private
name|JList
name|familyList
decl_stmt|;
DECL|field|sizeField
specifier|private
name|JTextField
name|sizeField
decl_stmt|;
DECL|field|sizeList
specifier|private
name|JList
name|sizeList
decl_stmt|;
DECL|field|styleField
specifier|private
name|JTextField
name|styleField
decl_stmt|;
DECL|field|styleList
specifier|private
name|JList
name|styleList
decl_stmt|;
DECL|field|preview
specifier|private
name|JLabel
name|preview
decl_stmt|;
DECL|field|ok
specifier|private
name|JButton
name|ok
decl_stmt|;
DECL|field|cancel
specifier|private
name|JButton
name|cancel
decl_stmt|;
comment|/** 	 * For some reason the default Java fonts show up in the 	 * list with .bold, .bolditalic, and .italic extensions. 	 */
DECL|field|HIDEFONTS
specifier|private
specifier|static
specifier|final
name|String
index|[]
name|HIDEFONTS
init|=
block|{
literal|".bold"
block|,
literal|".italic"
block|}
decl_stmt|;
comment|// [pending] from GeneralCustomizer :
comment|// GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()
DECL|method|getFontList ()
specifier|private
name|String
index|[]
name|getFontList
parameter_list|()
block|{
try|try
block|{
name|Class
name|GEClass
init|=
name|Class
operator|.
name|forName
argument_list|(
literal|"java.awt.GraphicsEnvironment"
argument_list|)
decl_stmt|;
name|Object
name|GEInstance
init|=
name|GEClass
operator|.
name|getMethod
argument_list|(
literal|"getLocalGraphicsEnvironment"
argument_list|,
literal|null
argument_list|)
operator|.
name|invoke
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|String
index|[]
name|nameArray
init|=
operator|(
name|String
index|[]
operator|)
name|GEClass
operator|.
name|getMethod
argument_list|(
literal|"getAvailableFontFamilyNames"
argument_list|,
literal|null
argument_list|)
operator|.
name|invoke
argument_list|(
name|GEInstance
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|Vector
name|nameVector
init|=
operator|new
name|Vector
argument_list|(
name|nameArray
operator|.
name|length
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|,
name|j
init|;
name|i
operator|<
name|nameArray
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
for|for
control|(
name|j
operator|=
literal|0
init|;
name|j
operator|<
name|HIDEFONTS
operator|.
name|length
condition|;
name|j
operator|++
control|)
block|{
if|if
condition|(
name|nameArray
index|[
name|i
index|]
operator|.
name|indexOf
argument_list|(
name|HIDEFONTS
index|[
name|j
index|]
argument_list|)
operator|>=
literal|0
condition|)
break|break;
block|}
if|if
condition|(
name|j
operator|==
name|HIDEFONTS
operator|.
name|length
condition|)
name|nameVector
operator|.
name|addElement
argument_list|(
name|nameArray
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
name|String
index|[]
name|_array
init|=
operator|new
name|String
index|[
name|nameVector
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
name|nameVector
operator|.
name|copyInto
argument_list|(
name|_array
argument_list|)
expr_stmt|;
return|return
name|_array
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
return|return
literal|null
return|;
comment|//return Toolkit.getDefaultToolkit().getFontList();
block|}
block|}
DECL|method|createTextFieldAndListPanel (String label,JTextField textField, JList list)
specifier|private
name|JPanel
name|createTextFieldAndListPanel
parameter_list|(
name|String
name|label
parameter_list|,
name|JTextField
name|textField
parameter_list|,
name|JList
name|list
parameter_list|)
block|{
name|GridBagLayout
name|layout
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
name|JPanel
name|panel
init|=
operator|new
name|JPanel
argument_list|(
name|layout
argument_list|)
decl_stmt|;
name|GridBagConstraints
name|cons
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
name|cons
operator|.
name|gridx
operator|=
name|cons
operator|.
name|gridy
operator|=
literal|0
expr_stmt|;
name|cons
operator|.
name|gridwidth
operator|=
name|cons
operator|.
name|gridheight
operator|=
literal|1
expr_stmt|;
name|cons
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
expr_stmt|;
name|cons
operator|.
name|weightx
operator|=
literal|1.0f
expr_stmt|;
name|JLabel
name|_label
init|=
operator|new
name|JLabel
argument_list|(
name|label
argument_list|)
decl_stmt|;
name|layout
operator|.
name|setConstraints
argument_list|(
name|_label
argument_list|,
name|cons
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|_label
argument_list|)
expr_stmt|;
name|cons
operator|.
name|gridy
operator|=
literal|1
expr_stmt|;
name|Component
name|vs
init|=
name|Box
operator|.
name|createVerticalStrut
argument_list|(
literal|6
argument_list|)
decl_stmt|;
name|layout
operator|.
name|setConstraints
argument_list|(
name|vs
argument_list|,
name|cons
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|vs
argument_list|)
expr_stmt|;
name|cons
operator|.
name|gridy
operator|=
literal|2
expr_stmt|;
name|layout
operator|.
name|setConstraints
argument_list|(
name|textField
argument_list|,
name|cons
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|textField
argument_list|)
expr_stmt|;
name|cons
operator|.
name|gridy
operator|=
literal|3
expr_stmt|;
name|vs
operator|=
name|Box
operator|.
name|createVerticalStrut
argument_list|(
literal|6
argument_list|)
expr_stmt|;
name|layout
operator|.
name|setConstraints
argument_list|(
name|vs
argument_list|,
name|cons
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|vs
argument_list|)
expr_stmt|;
name|cons
operator|.
name|gridy
operator|=
literal|4
expr_stmt|;
name|cons
operator|.
name|gridheight
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|cons
operator|.
name|weighty
operator|=
literal|1.0f
expr_stmt|;
name|JScrollPane
name|scroller
init|=
operator|new
name|JScrollPane
argument_list|(
name|list
argument_list|)
decl_stmt|;
name|layout
operator|.
name|setConstraints
argument_list|(
name|scroller
argument_list|,
name|cons
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|scroller
argument_list|)
expr_stmt|;
return|return
name|panel
return|;
block|}
DECL|method|updatePreview ()
specifier|private
name|void
name|updatePreview
parameter_list|()
block|{
name|String
name|family
init|=
name|familyField
operator|.
name|getText
argument_list|()
decl_stmt|;
name|int
name|size
decl_stmt|;
try|try
block|{
name|size
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|sizeField
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|size
operator|=
literal|14
expr_stmt|;
block|}
name|int
name|style
init|=
name|styleList
operator|.
name|getSelectedIndex
argument_list|()
decl_stmt|;
name|preview
operator|.
name|setFont
argument_list|(
operator|new
name|Font
argument_list|(
name|family
argument_list|,
name|style
argument_list|,
name|size
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|class|ActionHandler
class|class
name|ActionHandler
implements|implements
name|ActionListener
block|{
DECL|method|actionPerformed (ActionEvent evt)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|evt
parameter_list|)
block|{
if|if
condition|(
name|evt
operator|.
name|getSource
argument_list|()
operator|==
name|ok
condition|)
name|ok
argument_list|()
expr_stmt|;
elseif|else
if|if
condition|(
name|evt
operator|.
name|getSource
argument_list|()
operator|==
name|cancel
condition|)
name|cancel
argument_list|()
expr_stmt|;
block|}
block|}
DECL|class|ListHandler
class|class
name|ListHandler
implements|implements
name|ListSelectionListener
block|{
DECL|method|valueChanged (ListSelectionEvent evt)
specifier|public
name|void
name|valueChanged
parameter_list|(
name|ListSelectionEvent
name|evt
parameter_list|)
block|{
name|Object
name|source
init|=
name|evt
operator|.
name|getSource
argument_list|()
decl_stmt|;
if|if
condition|(
name|source
operator|==
name|familyList
condition|)
block|{
name|String
name|family
init|=
operator|(
name|String
operator|)
name|familyList
operator|.
name|getSelectedValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|family
operator|!=
literal|null
condition|)
name|familyField
operator|.
name|setText
argument_list|(
name|family
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|source
operator|==
name|sizeList
condition|)
block|{
name|String
name|size
init|=
operator|(
name|String
operator|)
name|sizeList
operator|.
name|getSelectedValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|size
operator|!=
literal|null
condition|)
name|sizeField
operator|.
name|setText
argument_list|(
name|size
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|source
operator|==
name|styleList
condition|)
block|{
name|String
name|style
init|=
operator|(
name|String
operator|)
name|styleList
operator|.
name|getSelectedValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|style
operator|!=
literal|null
condition|)
name|styleField
operator|.
name|setText
argument_list|(
name|style
argument_list|)
expr_stmt|;
block|}
name|updatePreview
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|main (String args[])
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
name|args
index|[]
parameter_list|)
block|{
name|Font
name|font
init|=
operator|new
name|FontSelectorDialog
argument_list|(
literal|null
argument_list|,
operator|new
name|Font
argument_list|(
literal|"Times"
argument_list|,
name|Font
operator|.
name|PLAIN
argument_list|,
literal|12
argument_list|)
argument_list|)
operator|.
name|getSelectedFont
argument_list|()
decl_stmt|;
block|}
block|}
end_class

end_unit

