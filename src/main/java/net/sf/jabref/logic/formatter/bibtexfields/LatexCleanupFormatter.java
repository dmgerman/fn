begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.formatter.bibtexfields
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
package|;
end_package

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

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|cleanup
operator|.
name|Formatter
import|;
end_import

begin_class
DECL|class|LatexCleanupFormatter
specifier|public
class|class
name|LatexCleanupFormatter
implements|implements
name|Formatter
block|{
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"LaTeX cleanup"
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getKey ()
specifier|public
name|String
name|getKey
parameter_list|()
block|{
return|return
literal|"latex_cleanup"
return|;
block|}
annotation|@
name|Override
DECL|method|format (String oldString)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|oldString
parameter_list|)
block|{
name|String
name|newValue
init|=
name|oldString
decl_stmt|;
comment|// Remove redundant $, {, and }, but not if the } is part of a command argument: \mbox{-}{GPS} should not be adjusted
name|newValue
operator|=
name|newValue
operator|.
name|replace
argument_list|(
literal|"$$"
argument_list|,
literal|""
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"(?<!\\\\[\\p{Alpha}]{0,100}\\{[^\\}]{0,100})\\}([-/ ]?)\\{"
argument_list|,
literal|"$1"
argument_list|)
expr_stmt|;
comment|// Move numbers, +, -, /, and brackets into equations
name|newValue
operator|=
name|newValue
operator|.
name|replaceAll
argument_list|(
literal|"(([^$]|\\\\\\$)*)\\$"
argument_list|,
literal|"$1@@"
argument_list|)
expr_stmt|;
comment|// Replace $, but not \$ with @@
name|newValue
operator|=
name|newValue
operator|.
name|replaceAll
argument_list|(
literal|"([^@]*)@@([^@]*)@@"
argument_list|,
literal|"$1\\$$2@@"
argument_list|)
expr_stmt|;
comment|// Replace every other @@ with $
comment|//newValue = newValue.replaceAll("([0-9\\(\\.]+) \\$","\\$$1\\\\ "); // Move numbers followed by a space left of $ inside the equation, e.g., 0.35 $\mu$m
name|newValue
operator|=
name|newValue
operator|.
name|replaceAll
argument_list|(
literal|"([0-9\\(\\.]+[ ]?[-+/]?[ ]?)\\$"
argument_list|,
literal|"\\$$1"
argument_list|)
expr_stmt|;
comment|// Move numbers, possibly with operators +, -, or /,  left of $ into the equation
name|newValue
operator|=
name|newValue
operator|.
name|replaceAll
argument_list|(
literal|"@@([ ]?[-+/]?[ ]?[0-9\\)\\.]+)"
argument_list|,
literal|" $1@@"
argument_list|)
expr_stmt|;
comment|// Move numbers right of @@ into the equation
name|newValue
operator|=
name|newValue
operator|.
name|replace
argument_list|(
literal|"@@"
argument_list|,
literal|"$"
argument_list|)
expr_stmt|;
comment|// Replace all @@ with $
name|newValue
operator|=
name|newValue
operator|.
name|replace
argument_list|(
literal|"  "
argument_list|,
literal|" "
argument_list|)
expr_stmt|;
comment|// Clean up
name|newValue
operator|=
name|newValue
operator|.
name|replace
argument_list|(
literal|"$$"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|newValue
operator|=
name|newValue
operator|.
name|replace
argument_list|(
literal|" )$"
argument_list|,
literal|")$"
argument_list|)
expr_stmt|;
name|newValue
operator|=
name|newValue
operator|.
name|replace
argument_list|(
literal|"%"
argument_list|,
literal|"\\%"
argument_list|)
expr_stmt|;
comment|// escape % used for comments in TeX
return|return
name|newValue
return|;
block|}
annotation|@
name|Override
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cleans up LaTeX code."
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getExampleInput ()
specifier|public
name|String
name|getExampleInput
parameter_list|()
block|{
return|return
literal|"{VLSI} {DSP}"
return|;
block|}
block|}
end_class

end_unit

