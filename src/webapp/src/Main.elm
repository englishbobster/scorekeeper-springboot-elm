port module ScoreKeeper exposing (..)
import Browser
import Html exposing (Html, div)
import Html.Events exposing (onClick)
import Json.Decode


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
  div [] []


-- Main

main: Program () Model Msg
main =
    Browser.sandbox { init = initialModel, update = update, view = view }
