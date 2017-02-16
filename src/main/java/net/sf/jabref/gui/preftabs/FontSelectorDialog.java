begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.preftabs
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|preftabs
package|;
end_package

begin_comment
comment|/*   Taken from JpicEdt modified slightly by nizar batada for JabRef   EepicViewFactory.java - February 11, 2002 - jPicEdt, a picture editor for LaTeX.  copyright (C) 1999-2002 Sylvain Reynal  Portions copyright (C) 2000, 2001 Slava Pestov  Portions copyright (C) 1999 Jason Ginchereau   D\uFFFDpartement de Physique  Ecole Nationale Sup\uFFFDrieure de l'Electronique et de ses Applications (ENSEA)  6, avenue du Ponceau  F-95014 CERGY CEDEX   Tel : +33 130 736 245  Fax : +33 130 736 667  e-mail : reynal@ensea.fr  jPicEdt web page : http://trashx.ensea.fr/jpicedt/   This program is free software; you can redistribute it and/or  modify it under the terms of the GNU General Public License  as published by the Free Software Foundation; either version 2  of the License, or any later version.   This program is distributed in the hope that it will be useful,  but WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  GNU General Public License for more details.   You should have received a copy of the GNU General Public License  along with this program; if not, write to the Free Software  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.  */
end_comment

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|BorderLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Dimension
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Font
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Graphics
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Graphics2D
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|GraphicsEnvironment
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|GridBagConstraints
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|GridBagLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|GridLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|RenderingHints
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|Box
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|BoxLayout
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JButton
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JDialog
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JLabel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JList
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JOptionPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPanel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JScrollPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JTextField
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
name|EmptyBorder
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
name|TitledBorder
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|l10n
operator|.
name|Localization
import|;
end_import

begin_comment
comment|/**  * A font chooser widget.  * @author Slava Pestov (jEdit), Sylvain Reynal  * @since jpicedt 1.3.2.beta-9  * @version $Id$  *<p>  * $Log$  * Revision 1.8  2007/07/19 01:35:35  coezbek  * JabRef 2.4b1 Plug-In System established. Yeah!  *  * Revision 1.7  2006/04/26 08:46:57  kiar  * fix dialog.show() deprecation messages, change build.xml  *  * Revision 1.6  2004/02/27 23:28:41  mortenalver  * Some code tidying, no effect on behaviour (hopefully)  *  * Revision 1.5  2004/02/24 23:30:18  mortenalver  * Added more translations, and started work on a Replace string feature  *  * Revision 1.4  2004/02/17 09:14:02  mortenalver  * Similar update in Font selection preview.  *  * Revision 1.3  2004/02/17 07:35:22  mortenalver  * Experimenting with antialiasing in table.  *  * Revision 1.2  2003/12/14 23:48:02  mortenalver  * .  *  * Revision 1.1  2003/11/07 22:18:07  nbatada  * modified it slightly from initial version  *  * Revision 1.1  2003/11/07 22:14:34  nbatada  * modified it from initial version  *  * Revision 1.4  2003/11/02 01:51:06  reynal  * Cleaned-up i18n labels  *  * Revision 1.3  2003/08/31 22:05:40  reynal  *  * Enhanced class interface for some widgets.  *   */
end_comment

begin_comment
comment|///////////////////////////////////////////////////////////////////////////////
end_comment

begin_class
DECL|class|FontSelectorDialog
specifier|public
class|class
name|FontSelectorDialog
extends|extends
name|JDialog
block|{
DECL|field|PLAIN
specifier|private
specifier|static
specifier|final
name|String
name|PLAIN
init|=
literal|"plain"
decl_stmt|;
DECL|field|BOLD
specifier|private
specifier|static
specifier|final
name|String
name|BOLD
init|=
literal|"bold"
decl_stmt|;
DECL|field|BOLD_ITALIC
specifier|private
specifier|static
specifier|final
name|String
name|BOLD_ITALIC
init|=
literal|"bold-italic"
decl_stmt|;
DECL|field|ITALIC
specifier|private
specifier|static
specifier|final
name|String
name|ITALIC
init|=
literal|"italic"
decl_stmt|;
DECL|field|styles
specifier|private
specifier|static
specifier|final
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
DECL|field|sizes
specifier|private
specifier|static
specifier|final
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
comment|// private members
DECL|field|isOK
specifier|private
name|boolean
name|isOK
decl_stmt|;
DECL|field|familyField
specifier|private
specifier|final
name|JTextField
name|familyField
init|=
operator|new
name|JTextField
argument_list|()
decl_stmt|;
DECL|field|familyList
specifier|private
specifier|final
name|JList
argument_list|<
name|String
argument_list|>
name|familyList
decl_stmt|;
DECL|field|sizeField
specifier|private
specifier|final
name|JTextField
name|sizeField
init|=
operator|new
name|JTextField
argument_list|()
decl_stmt|;
DECL|field|sizeList
specifier|private
specifier|final
name|JList
argument_list|<
name|String
argument_list|>
name|sizeList
init|=
operator|new
name|JList
argument_list|<>
argument_list|(
name|sizes
argument_list|)
decl_stmt|;
DECL|field|styleField
specifier|private
specifier|final
name|JTextField
name|styleField
init|=
operator|new
name|JTextField
argument_list|()
decl_stmt|;
DECL|field|styleList
specifier|private
specifier|final
name|JList
argument_list|<
name|String
argument_list|>
name|styleList
init|=
operator|new
name|JList
argument_list|<>
argument_list|(
name|styles
argument_list|)
decl_stmt|;
DECL|field|preview
specifier|private
specifier|final
name|JLabel
name|preview
decl_stmt|;
comment|/**      * For some reason the default Java fonts show up in the      * list with .bold, .bolditalic, and .italic extensions.      */
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
name|super
argument_list|(
name|JOptionPane
operator|.
name|getFrameForComponent
argument_list|(
name|comp
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Font selection"
argument_list|)
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
name|familyList
operator|=
operator|new
name|JList
argument_list|<>
argument_list|(
name|getFontList
argument_list|()
argument_list|)
expr_stmt|;
name|JPanel
name|familyPanel
init|=
name|createTextFieldAndListPanel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Font family"
argument_list|)
argument_list|,
name|familyField
argument_list|,
name|familyList
argument_list|)
decl_stmt|;
name|listPanel
operator|.
name|add
argument_list|(
name|familyPanel
argument_list|)
expr_stmt|;
name|JPanel
name|sizePanel
init|=
name|createTextFieldAndListPanel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Font size"
argument_list|)
argument_list|,
name|sizeField
argument_list|,
name|sizeList
argument_list|)
decl_stmt|;
name|listPanel
operator|.
name|add
argument_list|(
name|sizePanel
argument_list|)
expr_stmt|;
name|JPanel
name|stylePanel
init|=
name|createTextFieldAndListPanel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Font style"
argument_list|)
argument_list|,
name|styleField
argument_list|,
name|styleList
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
name|styleList
operator|.
name|getSelectedValue
argument_list|()
argument_list|)
expr_stmt|;
name|familyList
operator|.
name|addListSelectionListener
argument_list|(
name|evt
lambda|->
block|{
name|String
name|family
init|=
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
block|{
name|familyField
operator|.
name|setText
argument_list|(
name|family
argument_list|)
expr_stmt|;
block|}
name|updatePreview
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|sizeList
operator|.
name|addListSelectionListener
argument_list|(
name|evt
lambda|->
block|{
name|String
name|size
init|=
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
block|{
name|sizeField
operator|.
name|setText
argument_list|(
name|size
argument_list|)
expr_stmt|;
block|}
name|updatePreview
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|styleList
operator|.
name|addListSelectionListener
argument_list|(
name|evt
lambda|->
block|{
name|String
name|style
init|=
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
block|{
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
comment|/* --------------------------------------------------------            |  Experimental addition by Morten Alver. I want to    |            |  enable antialiasing in the preview field, since I'm |            |  working on introducing this in the table view.      |            -------------------------------------------------------- */
name|preview
operator|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Font preview"
argument_list|)
argument_list|)
block|{
annotation|@
name|Override
specifier|public
name|void
name|paint
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
name|g2
operator|.
name|setRenderingHint
argument_list|(
name|RenderingHints
operator|.
name|KEY_ANTIALIASING
argument_list|,
name|RenderingHints
operator|.
name|VALUE_ANTIALIAS_ON
argument_list|)
expr_stmt|;
name|super
operator|.
name|paint
argument_list|(
name|g2
argument_list|)
expr_stmt|;
block|}
block|}
expr_stmt|;
name|preview
operator|.
name|setBorder
argument_list|(
operator|new
name|TitledBorder
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Font preview"
argument_list|)
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
name|JButton
name|ok
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"OK"
argument_list|)
argument_list|)
decl_stmt|;
name|ok
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
name|isOK
operator|=
literal|true
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
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
name|JButton
name|cancel
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|)
decl_stmt|;
name|cancel
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|dispose
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
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
DECL|method|getSelectedFont ()
specifier|public
name|Optional
argument_list|<
name|Font
argument_list|>
name|getSelectedFont
parameter_list|()
block|{
if|if
condition|(
operator|!
name|isOK
condition|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
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
name|NumberFormatException
name|e
parameter_list|)
block|{
name|size
operator|=
literal|14
expr_stmt|;
block|}
return|return
name|Optional
operator|.
name|of
argument_list|(
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
argument_list|)
return|;
block|}
DECL|method|getFontList ()
specifier|private
specifier|static
name|String
index|[]
name|getFontList
parameter_list|()
block|{
try|try
block|{
name|String
index|[]
name|nameArray
init|=
name|GraphicsEnvironment
operator|.
name|getLocalGraphicsEnvironment
argument_list|()
operator|.
name|getAvailableFontFamilyNames
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|nameList
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|nameArray
operator|.
name|length
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|fontName
range|:
name|nameArray
control|)
block|{
name|boolean
name|hidden
init|=
literal|false
decl_stmt|;
for|for
control|(
name|String
name|hiddenName
range|:
name|FontSelectorDialog
operator|.
name|HIDEFONTS
control|)
block|{
if|if
condition|(
name|fontName
operator|.
name|contains
argument_list|(
name|hiddenName
argument_list|)
condition|)
block|{
name|hidden
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
operator|!
name|hidden
condition|)
block|{
name|nameList
operator|.
name|add
argument_list|(
name|fontName
argument_list|)
expr_stmt|;
block|}
block|}
name|String
index|[]
name|resultArray
init|=
operator|new
name|String
index|[
name|nameList
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
return|return
name|nameList
operator|.
name|toArray
argument_list|(
name|resultArray
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|SecurityException
decl||
name|IllegalArgumentException
name|ex
parameter_list|)
block|{
return|return
operator|new
name|String
index|[
literal|0
index|]
return|;
block|}
block|}
DECL|method|createTextFieldAndListPanel (String labelString, JTextField textField, JList<String> list)
specifier|private
specifier|static
name|JPanel
name|createTextFieldAndListPanel
parameter_list|(
name|String
name|labelString
parameter_list|,
name|JTextField
name|textField
parameter_list|,
name|JList
argument_list|<
name|String
argument_list|>
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
name|label
init|=
operator|new
name|JLabel
argument_list|(
name|labelString
argument_list|)
decl_stmt|;
name|layout
operator|.
name|setConstraints
argument_list|(
name|label
argument_list|,
name|cons
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|label
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
name|NumberFormatException
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
block|}
end_class

end_unit

