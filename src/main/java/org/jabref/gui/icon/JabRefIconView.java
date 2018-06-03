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
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|ObjectProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|SimpleObjectProperty
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
name|GlyphIcon
import|;
end_import

begin_import
import|import
name|org
operator|.
name|fxmisc
operator|.
name|easybind
operator|.
name|EasyBind
import|;
end_import

begin_class
DECL|class|JabRefIconView
specifier|public
class|class
name|JabRefIconView
extends|extends
name|GlyphIcon
argument_list|<
name|IconTheme
operator|.
name|JabRefIcons
argument_list|>
block|{
comment|/**      * This property is only needed to get proper IDE support in FXML files      * (e.g. validation that parameter passed to "icon" is indeed of type {@link IconTheme.JabRefIcons}).      */
DECL|field|glyph
specifier|private
name|ObjectProperty
argument_list|<
name|IconTheme
operator|.
name|JabRefIcons
argument_list|>
name|glyph
decl_stmt|;
DECL|method|JabRefIconView (IconTheme.JabRefIcons icon, String iconSize)
specifier|public
name|JabRefIconView
parameter_list|(
name|IconTheme
operator|.
name|JabRefIcons
name|icon
parameter_list|,
name|String
name|iconSize
parameter_list|)
block|{
name|super
argument_list|(
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|class
argument_list|)
expr_stmt|;
name|this
operator|.
name|glyph
operator|=
operator|new
name|SimpleObjectProperty
argument_list|<>
argument_list|(
name|icon
argument_list|)
expr_stmt|;
name|EasyBind
operator|.
name|subscribe
argument_list|(
name|glyph
argument_list|,
name|this
operator|::
name|setIcon
argument_list|)
expr_stmt|;
name|setIcon
argument_list|(
name|icon
argument_list|)
expr_stmt|;
name|setStyle
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"-fx-font-family: %s; -fx-font-size: %s;"
argument_list|,
name|icon
operator|.
name|fontFamily
argument_list|()
argument_list|,
name|iconSize
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|JabRefIconView (IconTheme.JabRefIcons icon)
specifier|public
name|JabRefIconView
parameter_list|(
name|IconTheme
operator|.
name|JabRefIcons
name|icon
parameter_list|)
block|{
name|this
argument_list|(
name|icon
argument_list|,
literal|"1em"
argument_list|)
expr_stmt|;
block|}
DECL|method|JabRefIconView ()
specifier|public
name|JabRefIconView
parameter_list|()
block|{
name|this
argument_list|(
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|ERROR
argument_list|,
literal|"1em"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getDefaultGlyph ()
specifier|public
name|IconTheme
operator|.
name|JabRefIcons
name|getDefaultGlyph
parameter_list|()
block|{
return|return
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|ERROR
return|;
block|}
DECL|method|getGlyph ()
specifier|public
name|IconTheme
operator|.
name|JabRefIcons
name|getGlyph
parameter_list|()
block|{
return|return
name|glyph
operator|.
name|get
argument_list|()
return|;
block|}
DECL|method|setGlyph (IconTheme.JabRefIcons icon)
specifier|public
name|void
name|setGlyph
parameter_list|(
name|IconTheme
operator|.
name|JabRefIcons
name|icon
parameter_list|)
block|{
name|this
operator|.
name|glyph
operator|.
name|set
argument_list|(
name|icon
argument_list|)
expr_stmt|;
block|}
DECL|method|glyphProperty ()
specifier|public
name|ObjectProperty
argument_list|<
name|IconTheme
operator|.
name|JabRefIcons
argument_list|>
name|glyphProperty
parameter_list|()
block|{
return|return
name|glyph
return|;
block|}
block|}
end_class

end_unit

