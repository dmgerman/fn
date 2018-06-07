begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.icon
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|icon
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
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
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|Icon
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|Node
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|paint
operator|.
name|Color
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|text
operator|.
name|Text
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|util
operator|.
name|ColorUtil
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_import
import|import
name|de
operator|.
name|jensd
operator|.
name|fx
operator|.
name|glyphs
operator|.
name|GlyphIcons
import|;
end_import

begin_class
DECL|class|InternalMaterialDesignIcon
specifier|public
class|class
name|InternalMaterialDesignIcon
implements|implements
name|JabRefIcon
block|{
DECL|field|icons
specifier|private
specifier|final
name|List
argument_list|<
name|GlyphIcons
argument_list|>
name|icons
decl_stmt|;
DECL|field|color
specifier|private
name|Optional
argument_list|<
name|Color
argument_list|>
name|color
decl_stmt|;
DECL|field|unicode
specifier|private
specifier|final
name|String
name|unicode
decl_stmt|;
DECL|method|InternalMaterialDesignIcon (java.awt.Color color, GlyphIcons... icons)
specifier|public
name|InternalMaterialDesignIcon
parameter_list|(
name|java
operator|.
name|awt
operator|.
name|Color
name|color
parameter_list|,
name|GlyphIcons
modifier|...
name|icons
parameter_list|)
block|{
name|this
argument_list|(
name|ColorUtil
operator|.
name|toFX
argument_list|(
name|color
argument_list|)
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
name|icons
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|InternalMaterialDesignIcon (Color color, GlyphIcons... icons)
specifier|public
name|InternalMaterialDesignIcon
parameter_list|(
name|Color
name|color
parameter_list|,
name|GlyphIcons
modifier|...
name|icons
parameter_list|)
block|{
name|this
argument_list|(
name|color
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
name|icons
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|InternalMaterialDesignIcon (Color color, List<GlyphIcons> icons)
name|InternalMaterialDesignIcon
parameter_list|(
name|Color
name|color
parameter_list|,
name|List
argument_list|<
name|GlyphIcons
argument_list|>
name|icons
parameter_list|)
block|{
name|this
argument_list|(
name|icons
argument_list|)
expr_stmt|;
name|this
operator|.
name|color
operator|=
name|Optional
operator|.
name|of
argument_list|(
name|color
argument_list|)
expr_stmt|;
block|}
DECL|method|InternalMaterialDesignIcon (GlyphIcons... icons)
specifier|public
name|InternalMaterialDesignIcon
parameter_list|(
name|GlyphIcons
modifier|...
name|icons
parameter_list|)
block|{
name|this
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|icons
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|InternalMaterialDesignIcon (List<GlyphIcons> icons)
specifier|public
name|InternalMaterialDesignIcon
parameter_list|(
name|List
argument_list|<
name|GlyphIcons
argument_list|>
name|icons
parameter_list|)
block|{
name|this
operator|.
name|icons
operator|=
name|icons
expr_stmt|;
name|this
operator|.
name|unicode
operator|=
name|icons
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|GlyphIcons
operator|::
name|unicode
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|joining
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|color
operator|=
name|Optional
operator|.
name|empty
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getIcon ()
specifier|public
name|Icon
name|getIcon
parameter_list|()
block|{
return|return
operator|new
name|IconTheme
operator|.
name|FontBasedIcon
argument_list|(
name|this
operator|.
name|unicode
argument_list|,
name|ColorUtil
operator|.
name|toAWT
argument_list|(
name|this
operator|.
name|color
operator|.
name|orElse
argument_list|(
name|IconTheme
operator|.
name|getDefaultColor
argument_list|()
argument_list|)
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getSmallIcon ()
specifier|public
name|Icon
name|getSmallIcon
parameter_list|()
block|{
return|return
operator|new
name|IconTheme
operator|.
name|FontBasedIcon
argument_list|(
name|this
operator|.
name|unicode
argument_list|,
name|ColorUtil
operator|.
name|toAWT
argument_list|(
name|this
operator|.
name|color
operator|.
name|orElse
argument_list|(
name|IconTheme
operator|.
name|getDefaultColor
argument_list|()
argument_list|)
argument_list|)
argument_list|,
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
operator|.
name|getInt
argument_list|(
name|JabRefPreferences
operator|.
name|ICON_SIZE_SMALL
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getGraphicNode ()
specifier|public
name|Node
name|getGraphicNode
parameter_list|()
block|{
name|GlyphIcons
name|icon
init|=
name|icons
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|Text
name|text
init|=
operator|new
name|Text
argument_list|(
name|icon
operator|.
name|unicode
argument_list|()
argument_list|)
decl_stmt|;
name|text
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"glyph-icon"
argument_list|)
expr_stmt|;
name|text
operator|.
name|setStyle
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"-fx-font-family: %s;"
argument_list|,
name|icon
operator|.
name|fontFamily
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|color
operator|.
name|ifPresent
argument_list|(
name|color
lambda|->
name|text
operator|.
name|setStyle
argument_list|(
name|text
operator|.
name|getStyle
argument_list|()
operator|+
name|String
operator|.
name|format
argument_list|(
literal|"-fx-fill: %s;"
argument_list|,
name|ColorUtil
operator|.
name|toRGBCode
argument_list|(
name|color
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|text
return|;
block|}
annotation|@
name|Override
DECL|method|disabled ()
specifier|public
name|JabRefIcon
name|disabled
parameter_list|()
block|{
return|return
operator|new
name|InternalMaterialDesignIcon
argument_list|(
name|ColorUtil
operator|.
name|toFX
argument_list|(
name|IconTheme
operator|.
name|DEFAULT_DISABLED_COLOR
argument_list|)
argument_list|,
name|icons
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|withColor (Color color)
specifier|public
name|JabRefIcon
name|withColor
parameter_list|(
name|Color
name|color
parameter_list|)
block|{
return|return
operator|new
name|InternalMaterialDesignIcon
argument_list|(
name|color
argument_list|,
name|icons
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|name ()
specifier|public
name|String
name|name
parameter_list|()
block|{
return|return
name|unicode
return|;
block|}
DECL|method|getCode ()
specifier|public
name|String
name|getCode
parameter_list|()
block|{
return|return
name|this
operator|.
name|unicode
return|;
block|}
annotation|@
name|Override
DECL|method|unicode ()
specifier|public
name|String
name|unicode
parameter_list|()
block|{
return|return
name|icons
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|unicode
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|fontFamily ()
specifier|public
name|String
name|fontFamily
parameter_list|()
block|{
return|return
name|icons
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|fontFamily
argument_list|()
return|;
block|}
block|}
end_class

end_unit
