<?php

namespace App\Imports;

use Illuminate\Support\Collection;
use Maatwebsite\Excel\Concerns\ToCollection;
use App\Models\Answer;
 // Add this line to import the 'Answer' class

class AnswersImport implements ToCollection
{
    /**
    * @param Collection $collection
    */
    public function collection(Collection $rows)
    {
        foreach ($rows as $row) 
        {
            if (count($row) < 3) {
                // Log an error or handle the case where the row doesn't have enough columns
                error_log("Row does not have enough columns: " . json_encode($row));
                continue; // Skip this row
            }
            
            $questionNo = (int) $row[0];
            $answer=(string) $row[1];
            $challengeNo = (int) $row[2];

            if ($questionNo == 0 && $answer == 'Answer' && $challengeNo == 0) {
                continue; // Skip the header row
            }
            

            if (!is_int($questionNo) || !is_int($challengeNo)) {
                // Log an error or handle the case where values are not integers
                error_log("Invalid data type for QuestionNo or ChallengeNo");
                continue; 
            }// Skip this row
            Answer::updateOrCreate(
                ['QuestionNo' => $questionNo],
                ['Answer' => $answer,
                'ChallengeNo' => $challengeNo,
                ]
            );
        }
    }
}

