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

begin_comment
comment|/**  * Provides the same true-type font interface as MaterialDesignIcon itself, but uses a font we created ourselves that  * contains icons that are not available in MaterialDesignIcons.  *  * @implNote The glyphs of the ttf (speak: the icons) were created with Illustrator and a template from the material design icons  * web-page. The art boards for each icon was exported as SVG and then converted with<a href="https://icomoon.io/app">  * IcoMoon</a>. The final TTF font is located in the resource folder.  * @see<a href="https://github.com/JabRef/jabref/wiki/Custom-SVG-Icons-for-JabRef">Tutorial on our Wiki</a>  * @see<a href="https://materialdesignicons.com/custom">Material Design Icon custom page</a>  */
end_comment

begin_enum
DECL|enum|JabRefMaterialDesignIcon
specifier|public
enum|enum
name|JabRefMaterialDesignIcon
implements|implements
name|GlyphIcons
block|{
DECL|enumConstant|TEX_STUDIO
name|TEX_STUDIO
argument_list|(
literal|"\ue900"
argument_list|)
block|,
DECL|enumConstant|TEX_MAKER
name|TEX_MAKER
argument_list|(
literal|"\ue901"
argument_list|)
block|,
DECL|enumConstant|EMACS
name|EMACS
argument_list|(
literal|"\ue902"
argument_list|)
block|,
DECL|enumConstant|OPEN_OFFICE
name|OPEN_OFFICE
argument_list|(
literal|"\ue903"
argument_list|)
block|,
DECL|enumConstant|VIM
name|VIM
argument_list|(
literal|"\ue904"
argument_list|)
block|,
DECL|enumConstant|VIM2
name|VIM2
argument_list|(
literal|"\ue905"
argument_list|)
block|,
DECL|enumConstant|LYX
name|LYX
argument_list|(
literal|"\ue906"
argument_list|)
block|,
DECL|enumConstant|WINEDT
name|WINEDT
argument_list|(
literal|"\ue907"
argument_list|)
block|,
DECL|enumConstant|ARXIV
name|ARXIV
argument_list|(
literal|"\ue908"
argument_list|)
block|,
DECL|enumConstant|COPY
name|COPY
argument_list|(
literal|"\ue909"
argument_list|)
block|,
DECL|enumConstant|PASTE
name|PASTE
argument_list|(
literal|"\ue90a"
argument_list|)
block|,
DECL|enumConstant|SET_CENTER
name|SET_CENTER
argument_list|(
literal|"\ue90b"
argument_list|)
block|,
DECL|enumConstant|SET_ALL
name|SET_ALL
argument_list|(
literal|"\ue90c"
argument_list|)
block|;
DECL|field|unicode
specifier|private
specifier|final
name|String
name|unicode
decl_stmt|;
DECL|method|JabRefMaterialDesignIcon (String unicode)
name|JabRefMaterialDesignIcon
parameter_list|(
name|String
name|unicode
parameter_list|)
block|{
name|this
operator|.
name|unicode
operator|=
name|unicode
expr_stmt|;
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
name|unicode
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
literal|"\'JabRefMaterialDesign\'"
return|;
block|}
block|}
end_enum

end_unit

