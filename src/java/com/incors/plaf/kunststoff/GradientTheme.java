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

begin_comment
comment|/*  * This code was developed by INCORS GmbH (www.incors.com).  * It is published under the terms of the GNU Lesser General Public License.  */
end_comment

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
name|javax
operator|.
name|swing
operator|.
name|plaf
operator|.
name|metal
operator|.
name|*
import|;
end_import

begin_comment
comment|/**  * Interface that provides methods for getting the colors for all gradients  * in the Kunststoff Look&Feel. This interface can be implemented by subclasses  * of<code>javax.swing.plaf.metal.MetalTheme</code> to have a theme that provides  * standard colors as well as gradient colors.  */
end_comment

begin_interface
DECL|interface|GradientTheme
specifier|public
interface|interface
name|GradientTheme
block|{
comment|/**    * Returns the upper gradient color for components like JButton, JMenuBar,    * and JProgressBar.    * Will return<code>null</code> if upper gradient should not be painted.    */
DECL|method|getComponentGradientColorReflection ()
specifier|public
name|ColorUIResource
name|getComponentGradientColorReflection
parameter_list|()
function_decl|;
comment|/**    * Returns the lower gradient color for components like JButton, JMenuBar,    * and JProgressBar.    * Will return<code>null</code> if lower gradient should not be painted.    */
DECL|method|getComponentGradientColorShadow ()
specifier|public
name|ColorUIResource
name|getComponentGradientColorShadow
parameter_list|()
function_decl|;
comment|/**    * Returns the upper gradient color for text components like JTextField and    * JPasswordField.    * Will return<code>null</code> if upper gradient should not be painted.    */
DECL|method|getTextComponentGradientColorReflection ()
specifier|public
name|ColorUIResource
name|getTextComponentGradientColorReflection
parameter_list|()
function_decl|;
comment|/**    * Returns the lower gradient color for text components like JTextField and    * JPasswordField.    * Will return<code>null</code> if lower gradient should not be painted.    */
DECL|method|getTextComponentGradientColorShadow ()
specifier|public
name|ColorUIResource
name|getTextComponentGradientColorShadow
parameter_list|()
function_decl|;
DECL|method|getBackgroundGradientShadow ()
specifier|public
name|int
name|getBackgroundGradientShadow
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

