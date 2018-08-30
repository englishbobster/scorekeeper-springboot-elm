port module ScoreKeeper exposing (..)

import Browser
import Html exposing (Html, h1, div, table, tbody, thead, th, tr, td, text)
import Html.Attributes exposing (class)
import Html.Events exposing (onClick)
import Json.Decode exposing (Decoder, int, string, field, map6)
import Http
import Url.Builder as Url
import Debug exposing (toString)


-- Types


type alias Match =
    { id : Int
    , homeTeam : String
    , awayTeam : String
    , matchTime : String
    , arena : String
    , group : String
    }


type Msg
    = PopulateInitialMatches (Result Http.Error Model)



-- Model


type alias Model =
    List Match


initialModel : Model
initialModel =
    []



-- Update


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    case msg of
        PopulateInitialMatches result ->
            case result of
                Ok newModel ->
                    ( newModel, Cmd.none )

                Err _ ->
                    ( model, Cmd.none )



-- View


view : Model -> Html Msg
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


makeFootballMatchRow : Match -> Html Msg
makeFootballMatchRow match =
    tr []
        [ td [] [ text (toString match.id) ]
        , td [] [ text match.homeTeam ]
        , td [] [ text match.awayTeam ]
        , td [] [ text match.matchTime ]
        , td [] [ text match.arena ]
        , td [] [ text match.group ]
        ]



-- Http


toAdminApiUrl : String
toAdminApiUrl =
    Url.crossOrigin "http://localhost:8080" [ "admin", "planned-matches" ] []


getTournamentMatchesRequest : Http.Request Model
getTournamentMatchesRequest =
    Http.get toAdminApiUrl matchListDecoder


getTournamentMatches : Cmd Msg
getTournamentMatches =
    Http.send PopulateInitialMatches (getTournamentMatchesRequest)



-- Json
{- For the admin api we need to decode the list of matches
   {
   "matchid": 1,
   "matchTime": "Thu, 12 Jun 2014 21:00:00 +0200",
   "arena": "Sao Paulo",
   "homeTeam": "Brazil",
   "awayTeam": "Croatia",
   "group": "Group A"
   }
-}


matchDecoder : Decoder Match
matchDecoder =
    map6 Match
        (field "matchid" int)
        (field "homeTeam" string)
        (field "awayTeam" string)
        (field "matchTime" string)
        (field "arena" string)
        (field "group" string)


matchListDecoder : Decoder Model
matchListDecoder =
    field "tournamentMatches" (Json.Decode.list matchDecoder)



-- Main


init : () -> ( Model, Cmd Msg )
init _ =
    ( initialModel, getTournamentMatches )


subscriptions : Model -> Sub Msg
subscriptions model =
    Sub.none


main : Program () Model Msg
main =
    Browser.element
        { init = init
        , update = update
        , subscriptions = subscriptions
        , view = view
        }
