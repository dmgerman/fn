begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.auxparser
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|auxparser
package|;
end_package

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Path
import|;
end_import

begin_interface
DECL|interface|AuxParser
specifier|public
interface|interface
name|AuxParser
block|{
comment|/**      * Executes the parsing logic and returns a result containing all information and the generated BibDatabase.      *      * @param auxFile Path to the LaTeX AUX file      * @return an AuxParserResult containing the generated BibDatabase and parsing statistics      */
DECL|method|parse (Path auxFile)
name|AuxParserResult
name|parse
parameter_list|(
name|Path
name|auxFile
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

