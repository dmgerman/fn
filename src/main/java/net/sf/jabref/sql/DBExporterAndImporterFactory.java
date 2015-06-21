begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.sql
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|sql
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
name|sql
operator|.
name|exporter
operator|.
name|DBExporter
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
name|sql
operator|.
name|exporter
operator|.
name|MySQLExporter
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
name|sql
operator|.
name|exporter
operator|.
name|PostgreSQLExporter
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
name|sql
operator|.
name|importer
operator|.
name|DBImporter
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
name|sql
operator|.
name|importer
operator|.
name|MySQLImporter
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
name|sql
operator|.
name|importer
operator|.
name|PostgreSQLImporter
import|;
end_import

begin_comment
comment|/**  * Created by ifsteinm  *   * Jan 20th 	This class is a factory that creates DBImporter and DBExporters  * 				when the user wishes to import or export a bib file to DBMS  *   */
end_comment

begin_class
DECL|class|DBExporterAndImporterFactory
specifier|public
class|class
name|DBExporterAndImporterFactory
block|{
comment|/**      * All DBTypes must appear here. The enum items must be the      * names that appear in the combobox used to select the DB,      * because this text is used to choose which DBImporter/Exporter      * will be sent back to the requester      *       */
DECL|enum|DBType
specifier|public
enum|enum
name|DBType
block|{
DECL|enumConstant|MYSQL
DECL|enumConstant|POSTGRESQL
name|MYSQL
argument_list|(
literal|"MYSQL"
argument_list|)
block|,
name|POSTGRESQL
argument_list|(
literal|"POSTGRESQL"
argument_list|)
block|;
DECL|field|dbType
specifier|private
name|String
name|dbType
decl_stmt|;
DECL|method|DBType (String dbType)
specifier|private
name|DBType
parameter_list|(
name|String
name|dbType
parameter_list|)
block|{
name|this
operator|.
name|dbType
operator|=
name|dbType
expr_stmt|;
block|}
DECL|method|getDBType ()
specifier|public
name|String
name|getDBType
parameter_list|()
block|{
return|return
name|dbType
return|;
block|}
block|}
comment|/**      * Returns a DBExporter object according to a given DBType      *       * @param type      * 		The type of the database selected      * @return The DBExporter object instance      */
DECL|method|getExporter (DBType type)
specifier|public
name|DBExporter
name|getExporter
parameter_list|(
name|DBType
name|type
parameter_list|)
block|{
name|DBExporter
name|exporter
init|=
literal|null
decl_stmt|;
switch|switch
condition|(
name|type
condition|)
block|{
case|case
name|MYSQL
case|:
name|exporter
operator|=
name|MySQLExporter
operator|.
name|getInstance
argument_list|()
expr_stmt|;
break|break;
case|case
name|POSTGRESQL
case|:
name|exporter
operator|=
name|PostgreSQLExporter
operator|.
name|getInstance
argument_list|()
expr_stmt|;
break|break;
block|}
return|return
name|exporter
return|;
block|}
comment|/**      * Returns a DBExporter object according the type given as a String      *       * @param type      * 		The type of the DB as a String. (e.g. Postgresql, MySQL)      * @return The DBExporter object instance      */
DECL|method|getExporter (String type)
specifier|public
name|DBExporter
name|getExporter
parameter_list|(
name|String
name|type
parameter_list|)
block|{
return|return
name|this
operator|.
name|getExporter
argument_list|(
name|DBType
operator|.
name|valueOf
argument_list|(
name|type
operator|.
name|toUpperCase
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Returns a DBImporter object according to a given DBType      *       * @param type      * 		The type of the database selected      * @return The DBImporter object instance      */
DECL|method|getImporter (DBType type)
specifier|public
name|DBImporter
name|getImporter
parameter_list|(
name|DBType
name|type
parameter_list|)
block|{
name|DBImporter
name|importer
init|=
literal|null
decl_stmt|;
switch|switch
condition|(
name|type
condition|)
block|{
case|case
name|MYSQL
case|:
name|importer
operator|=
name|MySQLImporter
operator|.
name|getInstance
argument_list|()
expr_stmt|;
break|break;
case|case
name|POSTGRESQL
case|:
name|importer
operator|=
name|PostgreSQLImporter
operator|.
name|getInstance
argument_list|()
expr_stmt|;
break|break;
block|}
return|return
name|importer
return|;
block|}
comment|/**      * Returns a DBImporter object according the type given as a String      *       * @param type      * 		The type of the DB as a String. (e.g. Postgresql, MySQL)      * @return The DBImporter object instance      */
DECL|method|getImporter (String type)
specifier|public
name|DBImporter
name|getImporter
parameter_list|(
name|String
name|type
parameter_list|)
block|{
return|return
name|this
operator|.
name|getImporter
argument_list|(
name|DBType
operator|.
name|valueOf
argument_list|(
name|type
operator|.
name|toUpperCase
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
block|}
end_class

end_unit

