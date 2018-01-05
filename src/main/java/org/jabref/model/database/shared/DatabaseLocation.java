begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.database.shared
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|shared
package|;
end_package

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|BibDatabaseContext
import|;
end_import

begin_comment
comment|/**  *  This enum represents the location for {@link BibDatabaseContext}.  */
end_comment

begin_enum
DECL|enum|DatabaseLocation
specifier|public
enum|enum
name|DatabaseLocation
block|{
DECL|enumConstant|LOCAL
name|LOCAL
block|,
DECL|enumConstant|SHARED
name|SHARED
block|; }
end_enum

end_unit

