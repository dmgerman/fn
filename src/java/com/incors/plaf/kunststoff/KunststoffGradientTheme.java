begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|com.incors.plaf.kunststoff
package|package
name|com
operator|.
name|incors
operator|.
name|plaf
operator|.
name|kunststoff
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|plaf
operator|.
name|*
import|;
end_import

begin_import
import|import
name|com
operator|.
name|incors
operator|.
name|plaf
operator|.
name|*
import|;
end_import

begin_comment
comment|/**  * DOCUMENT ME!  *  * @author $author$  * @version $Revision$  */
end_comment

begin_class
DECL|class|KunststoffGradientTheme
specifier|public
class|class
name|KunststoffGradientTheme
implements|implements
name|GradientTheme
block|{
comment|//~ Instance fields ////////////////////////////////////////////////////////
comment|// gradient colors
DECL|field|componentGradientColorReflection
specifier|private
specifier|final
name|ColorUIResource
name|componentGradientColorReflection
init|=
operator|new
name|ColorUIResource2
argument_list|(
literal|255
argument_list|,
literal|255
argument_list|,
literal|255
argument_list|,
literal|96
argument_list|)
decl_stmt|;
DECL|field|componentGradientColorShadow
specifier|private
specifier|final
name|ColorUIResource
name|componentGradientColorShadow
init|=
operator|new
name|ColorUIResource2
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|48
argument_list|)
decl_stmt|;
DECL|field|textComponentGradientColorReflection
specifier|private
specifier|final
name|ColorUIResource
name|textComponentGradientColorReflection
init|=
operator|new
name|ColorUIResource2
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|32
argument_list|)
decl_stmt|;
DECL|field|textComponentGradientColorShadow
specifier|private
specifier|final
name|ColorUIResource
name|textComponentGradientColorShadow
init|=
literal|null
decl_stmt|;
DECL|field|backgroundGradientShadow
specifier|private
specifier|final
name|int
name|backgroundGradientShadow
init|=
literal|32
decl_stmt|;
comment|//~ Methods ////////////////////////////////////////////////////////////////
DECL|method|getBackgroundGradientShadow ()
specifier|public
name|int
name|getBackgroundGradientShadow
parameter_list|()
block|{
return|return
name|backgroundGradientShadow
return|;
block|}
comment|// methods for getting gradient colors
DECL|method|getComponentGradientColorReflection ()
specifier|public
name|ColorUIResource
name|getComponentGradientColorReflection
parameter_list|()
block|{
return|return
name|componentGradientColorReflection
return|;
block|}
DECL|method|getComponentGradientColorShadow ()
specifier|public
name|ColorUIResource
name|getComponentGradientColorShadow
parameter_list|()
block|{
return|return
name|componentGradientColorShadow
return|;
block|}
comment|// methods
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Default Kunststoff Gradient Theme"
return|;
block|}
DECL|method|getTextComponentGradientColorReflection ()
specifier|public
name|ColorUIResource
name|getTextComponentGradientColorReflection
parameter_list|()
block|{
return|return
name|textComponentGradientColorReflection
return|;
block|}
DECL|method|getTextComponentGradientColorShadow ()
specifier|public
name|ColorUIResource
name|getTextComponentGradientColorShadow
parameter_list|()
block|{
return|return
name|textComponentGradientColorShadow
return|;
block|}
block|}
end_class

begin_comment
comment|///////////////////////////////////////////////////////////////////////////////
end_comment

begin_comment
comment|//  END OF FILE.
end_comment

begin_comment
comment|///////////////////////////////////////////////////////////////////////////////
end_comment

end_unit

