begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.msbib
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|msbib
package|;
end_package

begin_comment
comment|/**  * This class represents all supported MSBib entry types.  *<p>  * Book, BookSection, JournalArticle, ArticleInAPeriodical, ConferenceProceedings, Report,  * InternetSite, DocumentFromInternetSite, ElectronicSource, Art, SoundRecording, Performance,  * Film, Interview, Patent, Case, Misc  *  * See BIBFORM.XML, shared-bibliography.xsd (ECMA standard)  */
end_comment

begin_enum
DECL|enum|MSBibEntryType
specifier|public
enum|enum
name|MSBibEntryType
block|{
DECL|enumConstant|ArticleInAPeriodical
name|ArticleInAPeriodical
block|,
DECL|enumConstant|Book
name|Book
block|,
DECL|enumConstant|BookSection
name|BookSection
block|,
DECL|enumConstant|JournalArticle
name|JournalArticle
block|,
DECL|enumConstant|ConferenceProceedings
name|ConferenceProceedings
block|,
DECL|enumConstant|Report
name|Report
block|,
DECL|enumConstant|SoundRecording
name|SoundRecording
block|,
DECL|enumConstant|Performance
name|Performance
block|,
DECL|enumConstant|Art
name|Art
block|,
DECL|enumConstant|DocumentFromInternetSite
name|DocumentFromInternetSite
block|,
DECL|enumConstant|InternetSite
name|InternetSite
block|,
DECL|enumConstant|Film
name|Film
block|,
DECL|enumConstant|Interview
name|Interview
block|,
DECL|enumConstant|Patent
name|Patent
block|,
DECL|enumConstant|ElectronicSource
name|ElectronicSource
block|,
DECL|enumConstant|Case
name|Case
block|,
DECL|enumConstant|Misc
name|Misc
block|}
end_enum

end_unit
