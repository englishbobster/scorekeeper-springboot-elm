port module ScoreKeeper exposing (..)
import Browser
import Html exposing (Html, h1, div, table, tbody, thead, th, tr, td, text)
import Html.Attributes exposing (class)
import Html.Events exposing (onClick)
import Json.Decode
import Debug exposing (toString)


-- Types

type alias PlannedMatch =
    { id : Int
    , homeTeam : String
    , awayTeam : String
    , matchTime : String
    , arena : String
    , matchType : String
    }

type Msg = Noop


-- Model

type alias Model =
    List PlannedMatch

initialModel: Model
initialModel = []


-- Update

update: Msg -> Model -> Model
update msg model = model


-- View

view: Model -> Html Msg
view model =
  div [] [ matchTable model ]

matchTable : Model -> Html Msg
matchTable model =
    div [ class "datagrid" ]
        [ table
            [ class "datagrid" ]
            [ makeFootballMatchHeader
            , tbody [ class "datagrid" ] (List.map (\match -> makeFootballMatchRow match) model)
            ]
        , h1 [] [ text "debug info" ]
        , div [] [ text (toString model) ]
        ]

makeFootballMatchHeader : Html msg
makeFootballMatchHeader =
    thead [ class "datagrid" ]
        [ th [] [ text "Match Id" ]
        , th [] [ text "Home Team" ]
        , th [] [ text "Away Team" ]
        , th [] [ text "Date" ]
        , th [] [ text "Arena" ]
        , th [] [ text "Group/Round" ]
        ]

makeFootballMatchRow : PlannedMatch -> Html Msg
makeFootballMatchRow match =
    tr []
        [ td [] [ text (toString match.id) ]
        , td [] [ text match.homeTeam ]
        , td [] [ text match.awayTeam ]
        , td [] [ text match.matchTime ]
        , td [] [ text match.arena ]
        , td [] [ text match.matchType ]
        ]


-- Main

main: Program () Model Msg
main =
    Browser.sandbox { init = initialModel, update = update, view = view }
