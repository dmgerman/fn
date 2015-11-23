begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
package|;
end_package

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|layout
operator|.
name|FormLayout
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|builder
operator|.
name|FormBuilder
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
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|Globals
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|JabRefPreferences
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
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
comment|/**  * Created by IntelliJ IDEA.  * User: alver  * Date: Oct 10, 2005  * Time: 4:29:35 PM  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|ColorSetupPanel
specifier|public
class|class
name|ColorSetupPanel
extends|extends
name|JPanel
block|{
DECL|field|ICON_WIDTH
specifier|private
specifier|static
specifier|final
name|int
name|ICON_WIDTH
init|=
literal|30
decl_stmt|;
DECL|field|ICON_HEIGHT
specifier|private
specifier|static
specifier|final
name|int
name|ICON_HEIGHT
init|=
literal|20
decl_stmt|;
DECL|field|buttons
specifier|private
specifier|final
name|ArrayList
argument_list|<
name|ColorButton
argument_list|>
name|buttons
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|method|ColorSetupPanel ()
specifier|public
name|ColorSetupPanel
parameter_list|()
block|{
name|FormLayout
name|layout
init|=
operator|new
name|FormLayout
argument_list|(
literal|"30dlu, 4dlu, fill:pref, 4dlu, fill:pref, 8dlu, 30dlu, 4dlu, fill:pref, 4dlu, fill:pref"
argument_list|,
literal|"pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref"
argument_list|)
decl_stmt|;
name|FormBuilder
name|builder
init|=
name|FormBuilder
operator|.
name|create
argument_list|()
operator|.
name|layout
argument_list|(
name|layout
argument_list|)
decl_stmt|;
name|buttons
operator|.
name|add
argument_list|(
operator|new
name|ColorButton
argument_list|(
name|JabRefPreferences
operator|.
name|TABLE_TEXT
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Table text color"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|buttons
operator|.
name|add
argument_list|(
operator|new
name|ColorButton
argument_list|(
name|JabRefPreferences
operator|.
name|MARKED_ENTRY_BACKGROUND0
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Marking color %0"
argument_list|,
literal|"1"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|buttons
operator|.
name|add
argument_list|(
operator|new
name|ColorButton
argument_list|(
name|JabRefPreferences
operator|.
name|TABLE_BACKGROUND
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Table background color"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|buttons
operator|.
name|add
argument_list|(
operator|new
name|ColorButton
argument_list|(
name|JabRefPreferences
operator|.
name|MARKED_ENTRY_BACKGROUND1
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Marking color %0"
argument_list|,
literal|"2"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|buttons
operator|.
name|add
argument_list|(
operator|new
name|ColorButton
argument_list|(
name|JabRefPreferences
operator|.
name|TABLE_REQ_FIELD_BACKGROUND
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Background color for required fields"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|buttons
operator|.
name|add
argument_list|(
operator|new
name|ColorButton
argument_list|(
name|JabRefPreferences
operator|.
name|MARKED_ENTRY_BACKGROUND2
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Marking color %0"
argument_list|,
literal|"3"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|buttons
operator|.
name|add
argument_list|(
operator|new
name|ColorButton
argument_list|(
name|JabRefPreferences
operator|.
name|TABLE_OPT_FIELD_BACKGROUND
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Background color for optional fields"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|buttons
operator|.
name|add
argument_list|(
operator|new
name|ColorButton
argument_list|(
name|JabRefPreferences
operator|.
name|MARKED_ENTRY_BACKGROUND3
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Marking color %0"
argument_list|,
literal|"4"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|buttons
operator|.
name|add
argument_list|(
operator|new
name|ColorButton
argument_list|(
name|JabRefPreferences
operator|.
name|INCOMPLETE_ENTRY_BACKGROUND
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Color for marking incomplete entries"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|buttons
operator|.
name|add
argument_list|(
operator|new
name|ColorButton
argument_list|(
name|JabRefPreferences
operator|.
name|MARKED_ENTRY_BACKGROUND4
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Marking color %0"
argument_list|,
literal|"5"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|buttons
operator|.
name|add
argument_list|(
operator|new
name|ColorButton
argument_list|(
name|JabRefPreferences
operator|.
name|GRID_COLOR
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Table grid color"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|buttons
operator|.
name|add
argument_list|(
operator|new
name|ColorButton
argument_list|(
name|JabRefPreferences
operator|.
name|MARKED_ENTRY_BACKGROUND5
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Import marking color"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|buttons
operator|.
name|add
argument_list|(
operator|new
name|ColorButton
argument_list|(
name|JabRefPreferences
operator|.
name|FIELD_EDITOR_TEXT_COLOR
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Entry editor font color"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|buttons
operator|.
name|add
argument_list|(
operator|new
name|ColorButton
argument_list|(
name|JabRefPreferences
operator|.
name|VALID_FIELD_BACKGROUND_COLOR
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Entry editor background color"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|buttons
operator|.
name|add
argument_list|(
operator|new
name|ColorButton
argument_list|(
name|JabRefPreferences
operator|.
name|ACTIVE_FIELD_EDITOR_BACKGROUND_COLOR
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Entry editor active background color"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|buttons
operator|.
name|add
argument_list|(
operator|new
name|ColorButton
argument_list|(
name|JabRefPreferences
operator|.
name|INVALID_FIELD_BACKGROUND_COLOR
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Entry editor invalid field color"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|int
name|rowcnt
init|=
literal|0
decl_stmt|;
name|int
name|col
init|=
literal|0
decl_stmt|;
name|int
name|row
decl_stmt|;
for|for
control|(
name|ColorButton
name|but
range|:
name|buttons
control|)
block|{
name|row
operator|=
operator|(
literal|2
operator|*
operator|(
name|rowcnt
operator|/
literal|2
operator|)
operator|)
operator|+
literal|1
expr_stmt|;
comment|// == 2*floor(rowcnt/2) + 1
name|builder
operator|.
name|add
argument_list|(
operator|(
name|JButton
operator|)
name|but
argument_list|)
operator|.
name|xy
argument_list|(
operator|(
literal|6
operator|*
name|col
operator|)
operator|+
literal|1
argument_list|,
name|row
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|but
operator|.
name|getDefaultButton
argument_list|()
argument_list|)
operator|.
name|xy
argument_list|(
operator|(
literal|6
operator|*
name|col
operator|)
operator|+
literal|3
argument_list|,
name|row
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|but
operator|.
name|getName
argument_list|()
argument_list|)
operator|.
name|xy
argument_list|(
operator|(
literal|6
operator|*
name|col
operator|)
operator|+
literal|5
argument_list|,
name|row
argument_list|)
expr_stmt|;
name|but
operator|.
name|addActionListener
argument_list|(
operator|new
name|ColorButtonListener
argument_list|(
name|but
argument_list|)
argument_list|)
expr_stmt|;
name|col
operator|=
literal|1
operator|-
name|col
expr_stmt|;
comment|// Change 0 -> 1 -> 0 ...
name|rowcnt
operator|++
expr_stmt|;
block|}
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|builder
operator|.
name|getPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|setValues
argument_list|()
expr_stmt|;
block|}
DECL|method|setValues ()
specifier|public
name|void
name|setValues
parameter_list|()
block|{
for|for
control|(
name|ColorButton
name|but
range|:
name|buttons
control|)
block|{
name|but
operator|.
name|setColor
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
name|but
operator|.
name|getKey
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
block|{
for|for
control|(
name|ColorButton
name|but
range|:
name|buttons
control|)
block|{
name|Globals
operator|.
name|prefs
operator|.
name|putColor
argument_list|(
name|but
operator|.
name|getKey
argument_list|()
argument_list|,
name|but
operator|.
name|getColor
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
DECL|class|ColorButtonListener
class|class
name|ColorButtonListener
implements|implements
name|ActionListener
block|{
DECL|field|button
specifier|private
specifier|final
name|ColorButton
name|button
decl_stmt|;
DECL|method|ColorButtonListener (ColorButton button)
specifier|public
name|ColorButtonListener
parameter_list|(
name|ColorButton
name|button
parameter_list|)
block|{
name|this
operator|.
name|button
operator|=
name|button
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|Color
name|chosen
init|=
name|JColorChooser
operator|.
name|showDialog
argument_list|(
literal|null
argument_list|,
name|button
operator|.
name|getName
argument_list|()
argument_list|,
name|button
operator|.
name|getColor
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|chosen
operator|!=
literal|null
condition|)
block|{
name|button
operator|.
name|setColor
argument_list|(
name|chosen
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * A button to display the chosen color, and hold key information about a color setting.      * Includes a method to produce a Default button for this setting.      */
DECL|class|ColorButton
class|class
name|ColorButton
extends|extends
name|JButton
implements|implements
name|Icon
block|{
DECL|field|color
specifier|private
name|Color
name|color
init|=
name|Color
operator|.
name|white
decl_stmt|;
DECL|field|key
specifier|private
specifier|final
name|String
name|key
decl_stmt|;
DECL|field|name
specifier|private
specifier|final
name|String
name|name
decl_stmt|;
DECL|method|ColorButton (String key, String name)
specifier|public
name|ColorButton
parameter_list|(
name|String
name|key
parameter_list|,
name|String
name|name
parameter_list|)
block|{
name|setIcon
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|this
operator|.
name|key
operator|=
name|key
expr_stmt|;
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createRaisedBevelBorder
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|getDefaultButton ()
specifier|public
name|JButton
name|getDefaultButton
parameter_list|()
block|{
name|JButton
name|toDefault
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Default"
argument_list|)
argument_list|)
decl_stmt|;
name|toDefault
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|setColor
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getDefaultColor
argument_list|(
name|key
argument_list|)
argument_list|)
expr_stmt|;
name|repaint
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
return|return
name|toDefault
return|;
block|}
DECL|method|getKey ()
specifier|public
name|String
name|getKey
parameter_list|()
block|{
return|return
name|key
return|;
block|}
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
DECL|method|getColor ()
specifier|public
name|Color
name|getColor
parameter_list|()
block|{
return|return
name|color
return|;
block|}
DECL|method|setColor (Color color)
specifier|public
name|void
name|setColor
parameter_list|(
name|Color
name|color
parameter_list|)
block|{
name|this
operator|.
name|color
operator|=
name|color
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|paintIcon (Component c, Graphics g, int x, int y)
specifier|public
name|void
name|paintIcon
parameter_list|(
name|Component
name|c
parameter_list|,
name|Graphics
name|g
parameter_list|,
name|int
name|x
parameter_list|,
name|int
name|y
parameter_list|)
block|{
name|Rectangle
name|r
init|=
name|g
operator|.
name|getClipBounds
argument_list|()
decl_stmt|;
name|g
operator|.
name|setColor
argument_list|(
name|color
argument_list|)
expr_stmt|;
name|g
operator|.
name|fillRect
argument_list|(
name|r
operator|.
name|x
argument_list|,
name|r
operator|.
name|y
argument_list|,
name|r
operator|.
name|width
argument_list|,
name|r
operator|.
name|height
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getIconWidth ()
specifier|public
name|int
name|getIconWidth
parameter_list|()
block|{
return|return
name|ColorSetupPanel
operator|.
name|ICON_WIDTH
return|;
block|}
annotation|@
name|Override
DECL|method|getIconHeight ()
specifier|public
name|int
name|getIconHeight
parameter_list|()
block|{
return|return
name|ColorSetupPanel
operator|.
name|ICON_HEIGHT
return|;
block|}
block|}
block|}
end_class

end_unit

