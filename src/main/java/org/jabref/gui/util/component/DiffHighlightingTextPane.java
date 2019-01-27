begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.util.component
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|util
operator|.
name|component
package|;
end_package

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|text
operator|.
name|TextFlow
import|;
end_import

begin_class
DECL|class|DiffHighlightingTextPane
specifier|public
class|class
name|DiffHighlightingTextPane
extends|extends
name|TextFlow
block|{
DECL|field|BODY_STYLE
specifier|private
specifier|static
specifier|final
name|String
name|BODY_STYLE
init|=
literal|"body{font:sans-serif}"
decl_stmt|;
DECL|field|ADDITION_STYLE
specifier|private
specifier|static
specifier|final
name|String
name|ADDITION_STYLE
init|=
literal|".add{color:blue;text-decoration:underline}"
decl_stmt|;
DECL|field|REMOVAL_STYLE
specifier|private
specifier|static
specifier|final
name|String
name|REMOVAL_STYLE
init|=
literal|".del{color:red;text-decoration:line-through;}"
decl_stmt|;
DECL|field|CHANGE_STYLE
specifier|private
specifier|static
specifier|final
name|String
name|CHANGE_STYLE
init|=
literal|".change{color:#006400;text-decoration:underline}"
decl_stmt|;
DECL|field|CONTENT_TYPE
specifier|private
specifier|static
specifier|final
name|String
name|CONTENT_TYPE
init|=
literal|"text/html"
decl_stmt|;
DECL|method|DiffHighlightingTextPane ()
specifier|public
name|DiffHighlightingTextPane
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
comment|//        setContentType(CONTENT_TYPE);
comment|//        StyleSheet sheet = ((HTMLEditorKit) getEditorKit()).getStyleSheet();
comment|//        sheet.addRule(BODY_STYLE);
comment|//        sheet.addRule(ADDITION_STYLE);
comment|//        sheet.addRule(REMOVAL_STYLE);
comment|//        sheet.addRule(CHANGE_STYLE);
comment|//        setEditable(false);
block|}
block|}
end_class

end_unit

